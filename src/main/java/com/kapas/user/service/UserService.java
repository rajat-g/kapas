package com.kapas.user.service;

import com.kapas.user.entity.User;
import com.kapas.user.model.LoggedInUser;
import com.kapas.user.model.Login;
import com.kapas.user.model.UserSession;
import com.kapas.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionManagerService sessionManagerService;
    private final PasswordEncoder passwordEncoder;

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
}
