package com.test.task.expandapis_testtask.web;

import com.test.task.expandapis_testtask.DAO.User;
import com.test.task.expandapis_testtask.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public UserController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> register(@RequestBody AddUser addUser) {
        User registeredUser = authenticationService.signup(addUser);

        Response registerResponse = new Response();
        registerResponse.setUsername(registeredUser.getUsername());
        registerResponse.setPassword(registeredUser.getPassword());

        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody UserAuthentication userAuthentication) {
        User authenticatedUser = authenticationService.authenticate(userAuthentication);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Response loginResponse = new Response();
        loginResponse.setUsername(authenticatedUser.getUsername());
        loginResponse.setPassword(authenticatedUser.getPassword());

        return ResponseEntity.ok(loginResponse);
    }
}
