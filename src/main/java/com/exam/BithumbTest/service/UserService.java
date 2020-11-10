package com.exam.BithumbTest.service;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.dto.UserRequest;

import java.util.Optional;

public interface UserService {

    Long joinUser(UserRequest userRequest);
    Optional<UserEntity> findByEmail(String email);

}
