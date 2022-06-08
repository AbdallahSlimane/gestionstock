package com.abdproject.gestionstock.controller;

import com.abdproject.gestionstock.dto.auth.AuthentificationRequest;
import com.abdproject.gestionstock.dto.auth.AuthentificationResponse;
import com.abdproject.gestionstock.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abdproject.gestionstock.utils.Constants.AUTHENTIFICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTIFICATION_ENDPOINT)
public class AuthentificationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    private ResponseEntity<AuthentificationResponse> authenticate(@RequestBody AuthentificationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        final String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(AuthentificationResponse.builder().accessToken(jwt).build());
    }

}
