package com.exam.BithumbTest.config.security;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.domain.repository.UserRepository;
import com.exam.BithumbTest.dto.JwtResponse;
import com.exam.BithumbTest.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());


        UserEntity userEntity = userRepository.findByEmail(userDetails.getUsername()).get();
        // 직전로그인 update
        userEntity.setModifiedDt(LocalDateTime.now());
        userRepository.save(userEntity);

        response.addHeader(JwtTokenUtil.HEADER, JwtTokenUtil.PREFIX + " " + token);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new JwtResponse(JwtTokenUtil.PREFIX,token)));

    }
}
