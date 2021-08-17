package com.vuhien.application.controller;

import com.vuhien.application.entity.User;
import com.vuhien.application.model.request.LoginRequest;
import com.vuhien.application.model.request.RegisterRequest;
import com.vuhien.application.model.response.JwtResponse;
import com.vuhien.application.security.CustomUserDetail;
import com.vuhien.application.security.JwtProvider;
import com.vuhien.application.service.UserService;
import com.vuhien.application.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by VuHien96 on 17/08/2021 14:11
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<Object> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> signin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();

        return new ResponseEntity<>(new JwtResponse(
                token,
                customUserDetail.getFullName(),
                customUserDetail.getRoles()),
                HttpStatus.OK
        );
    }

    @GetMapping("/auth/confirm")
    public ResponseEntity<Object> confirmToken(@RequestParam String token) {
        verificationTokenService.confirmToken(token);
        return new ResponseEntity<>("Kích hoạt tài khoản thành công", HttpStatus.OK);
    }
}
