package com.test.task.expandapis_testtask.web;

import com.test.task.expandapis_testtask.Entitys.User;
import com.test.task.expandapis_testtask.Response.ResponseUser;
import com.test.task.expandapis_testtask.DTO.AddUserDTO;
import com.test.task.expandapis_testtask.DTO.UserAuthenticationDTO;
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
    public ResponseEntity<ResponseUser> register(@RequestBody AddUserDTO addUserDTO) {
        User registeredUser = authenticationService.signup(addUserDTO);

        ResponseUser registerResponseUser = new ResponseUser();
        registerResponseUser.setUsername(registeredUser.getUsername());
        registerResponseUser.setPassword(registeredUser.getPassword());

        return ResponseEntity.ok(registerResponseUser);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseUser> authenticate(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        User authenticatedUser = authenticationService.authenticate(userAuthenticationDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        ResponseUser loginResponseUser = new ResponseUser();
        loginResponseUser.setUsername(authenticatedUser.getUsername());
        loginResponseUser.setPassword(authenticatedUser.getPassword());

        return ResponseEntity.ok(loginResponseUser);
    }
}
