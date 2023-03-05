package com.kapas.user.service;

import com.kapas.user.entity.Role;
import com.kapas.user.entity.User;
import com.kapas.user.mapper.UserMapper;
import com.kapas.user.model.*;
import com.kapas.user.repository.RoleRepository;
import com.kapas.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionManagerService sessionManagerService;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private Role role;

    public LoggedInUser loginWithPassword(Login login) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByUserName(login.getUserName());
        User user = optionalUser.orElseThrow(() -> new Exception("Login failed. Provided data may not be correct."));
        if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            UserSession userSession = sessionManagerService.createSession(user);
            return new LoggedInUser(user, userSession);
        } else {
            throw new Exception("The password was incorrect.");
        }
    }

    public void deleteSession(String sessionId) {
        sessionManagerService.deleteSession(sessionId);
    }

    public void destroyAllUserSessions(User principal) {
        sessionManagerService.deleteForUser(principal);
    }

    @Transactional
    public UserResponse createUser( UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = userRepository.getReferenceById(1);
        role = roleRepository.getReferenceById(1);
        User saveUser = userMapper.userRequesttoUser(userRequest, user, role);
        userRepository.save(saveUser);
        return userMapper.userToUserResponse(saveUser);

    }

}
