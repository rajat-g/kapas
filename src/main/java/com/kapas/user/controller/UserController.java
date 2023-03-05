package com.kapas.user.controller;

import com.kapas.user.annotation.PermissionScopeValidation;
import com.kapas.user.entity.User;
import com.kapas.user.model.*;
import com.kapas.user.repository.UserRepository;
import com.kapas.user.service.UserService;
import com.kapas.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping(value = "/login")
    public ResponseEntity<LoggedInUser> login(@Valid @RequestBody Login login) throws Exception {
        LoggedInUser loggedInUser = userService.loginWithPassword(login);
        return new ResponseEntity<>(loggedInUser, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String sessionId = request.getHeader(Constants.SESSION_ID);
        userService.deleteSession(sessionId);
        String message = "Session destroyed successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<String> deleteAllSessions(HttpServletRequest request) {
        User principal = (User) request.getAttribute(Constants.PRINCIPAL);
        userService.destroyAllUserSessions(principal);
        return new ResponseEntity<>("All sessions of this user are deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    @PermissionScopeValidation(scope = ScopeEnum.TOKEN, permission = PermissionEnum.ADD)
    public ResponseEntity<String> test(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.PRINCIPAL);
        return new ResponseEntity<>(user.getEmail(), HttpStatus.OK);
    }

    @PostMapping(value= "/register")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserRequest userRequest){
        Optional<User> optionalUser =  userRepository.findUserByEmail(userRequest.getEmail());
        if (optionalUser.isEmpty()){
            UserResponse userResponse = userService.createUser(userRequest);
            return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User already exist", HttpStatus.OK);
        }
    }
}
