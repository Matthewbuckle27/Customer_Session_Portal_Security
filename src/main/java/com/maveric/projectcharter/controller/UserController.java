package com.maveric.projectcharter.controller;

import com.maveric.projectcharter.config.Constants;
import com.maveric.projectcharter.dto.AuthRequestDTO;
import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.UserDTO;
import com.maveric.projectcharter.exception.UserNotFoundException;
import com.maveric.projectcharter.jwt.JwtService;
import com.maveric.projectcharter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.USER_MAPPING)
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeleteArchiveResponse response;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping(Constants.CREATE_USER_MAPPING)
    public ResponseEntity<DeleteArchiveResponse> createUser(@RequestBody @Valid UserDTO userDTO) {
        response = userService.createUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(Constants.AUTHENTICATE_USER_MAPPING)
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequestDTO authRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            if(!userService.checkUserName(authRequest.getUsername())){
                throw new UserNotFoundException("Invalid User, please enter correct username");
            }
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            } else {
                throw new AuthenticationException("Authentication failed") {
                };
            }
        } catch (AuthenticationException authenticationException) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }
}
