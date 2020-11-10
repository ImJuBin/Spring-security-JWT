package com.exam.BithumbTest.service.impl;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.domain.repository.UserRepository;
import com.exam.BithumbTest.dto.UserRequest;
import com.exam.BithumbTest.enums.role.UserRole;
import com.exam.BithumbTest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    /**
     * 회원정보 저장
     */
    @Override
    public Long joinUser(UserRequest userRequest) {
        userRequest.setRole(UserRole.USER);
        userRequest.setPassword(encoder.encode(userRequest.getPassword())); // 단방향 해시 처리

        return userRepository.save(userRequest.toEntity()).getId();
    }

    /**
     * 회원가입 유효성실패 handling
     */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    /**
     * 회원정보 조회
     */
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
