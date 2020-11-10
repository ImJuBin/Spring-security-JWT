package com.exam.BithumbTest.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 권한정의(USER, ADMIN)
 */
@AllArgsConstructor
@Getter
public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;

}