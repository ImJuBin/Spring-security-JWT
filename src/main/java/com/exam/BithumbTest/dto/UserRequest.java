package com.exam.BithumbTest.dto;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.enums.role.UserRole;
import com.exam.BithumbTest.validation.PasswordValidation;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRequest {
    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @PasswordValidation
    private String password;

    private UserRole role;


    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    @Builder
    public UserRequest(Long id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
