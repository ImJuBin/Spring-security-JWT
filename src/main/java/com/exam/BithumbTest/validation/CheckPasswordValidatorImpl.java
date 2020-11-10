package com.exam.BithumbTest.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CheckPasswordValidatorImpl implements ConstraintValidator<PasswordValidation, String> , CheckPasswordValidator{

    //  비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.
    private final String pattern1 = "^(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&].{12,}$"; // 영어 소문자, 숫자, 특수문자, 12자리 이상
    private final String pattern2 = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&].{12,}$"; // 영어 대문자, 숫자, 특수문자, 12자리 이상
    private final String pattern3 = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&].{12,}$"; // 영어 대문자, 영어 소문자, 특수문자, 12자리 이상
    private final String pattern4 = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{12,}$"; // 영어 대문자, 영어 소문자, 숫자, 12자리 이상

    Matcher match;

    /**
     * 비밀번호 정규식 체크
     * @param password
     * @return boolean
     */
    @Override
    public boolean pwdRegularExpressionChk(String password) {
        boolean chk = false;

        // 영어 소문자, 숫자, 특수문자, 12자리 이상
        match = Pattern.compile(pattern1).matcher(password);
        if(match.find()) {
            chk = true;
        }
        // 영어 대문자, 숫자, 특수문자, 12자리 이상
        match = Pattern.compile(pattern2).matcher(password);
        if(match.find()) {
            chk = true;
        }
        // 영어 대문자, 영어 소문자, 특수문자, 12자리 이상
        match = Pattern.compile(pattern3).matcher(password);
        if(match.find()) {
            chk = true;
        }
        // 영어 대문자, 영어 소문자, 숫자, 12자리 이상
        match = Pattern.compile(pattern4).matcher(password);
        if(match.find()) {
            chk = true;
        }
        return chk;
    }

    @Override
    public boolean isValid(String pwd, ConstraintValidatorContext context) {
        boolean isPwdValidate = this.pwdRegularExpressionChk(pwd);

        if (!isPwdValidate) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    MessageFormat.format("비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.", context))
                    .addConstraintViolation();
        }
        return isPwdValidate;
    }
}
