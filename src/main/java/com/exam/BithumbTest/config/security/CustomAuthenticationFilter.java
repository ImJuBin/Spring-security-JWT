package com.exam.BithumbTest.config.security;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.exception.InputNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try{
            UserEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            authRequest = new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
        } catch (IOException exception){
            throw new InputNotFoundException();
        }

        setDetails(request, authRequest);

        return getAuthenticationManager().authenticate(authRequest);
    }
}
