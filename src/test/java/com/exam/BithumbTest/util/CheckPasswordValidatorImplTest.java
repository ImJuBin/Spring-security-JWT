package com.exam.BithumbTest.util;

import com.exam.BithumbTest.validation.CheckPasswordValidatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckPasswordValidatorImpl.class)
public class CheckPasswordValidatorImplTest {

    @Autowired
    private CheckPasswordValidatorImpl checkPasswordValidatorImpl;

    @Test
    public void 비밀번호유효성(){
        assertFalse(checkPasswordValidatorImpl.pwdRegularExpressionChk(""));
        assertFalse(checkPasswordValidatorImpl.pwdRegularExpressionChk("wnql45!@#$%"));
        assertFalse(checkPasswordValidatorImpl.pwdRegularExpressionChk("abcdefghijklmnop"));

        assertTrue(checkPasswordValidatorImpl.pwdRegularExpressionChk("wnqlsdl12345!@#$%"));
        assertTrue(checkPasswordValidatorImpl.pwdRegularExpressionChk("WNQLS!@#$%wnqls"));

    }

}

