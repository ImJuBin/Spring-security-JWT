package com.exam.BithumbTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime modifiedDt;

    public UserResponse(String email, String name, LocalDateTime modifiedDt) {
        this.email = email;
        this.name = name;
        this.modifiedDt = modifiedDt;
    }

    @Override
    public String toString() {
        return name + "(" + email + ") 님, 환영합니다.\n" +
                "(직전로그인 : " + Timestamp.valueOf(modifiedDt) + ")";
    }
}

