package com.exam.BithumbTest.controller;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.dto.UserRequest;
import com.exam.BithumbTest.dto.UserResponse;
import com.exam.BithumbTest.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class UserController {
    private final UserServiceImpl userService;

    /**
     * 회원가입 구현
     * @return HTTP 200,
     * 실패시 에러처리 handling, 성공시 회원가입 id return
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest userRequest, Errors errors) {
        // 회원가입 실패시
        if (errors.hasErrors()) {
            return ResponseEntity.ok(userService.validateHandling(errors));  // http 200, 유효성 통과 못한 필드와 메시지 return
        }
        Map<String, Long> map = new HashMap<>();
        map.put("id", userService.joinUser(userRequest));
        return ResponseEntity.ok(map); // http 200,insert pk값 return
    }


    /**
     * 로그인처리는 spring-security 아키텍쳐를 구현하였습니다.
     * {@link com.exam.BithumbTest.config.security.WebSecurityConfig}
     * CustomAuthenticationFilter > CustomAuthenticationProvider > 로그인 성공시 CustomLoginSuccessHandler , 로그인 실패 handling은 미구현
     * @return Jwt Token in Header & Body
     */
    @PostMapping("/login")
    public String login() {
        return "login";
    }


    /**
     * 회원가입 구현
     * /info url mapping시 JwtRequestFilter에서 토큰인증
     * @return HTTP 200,
     * 실패시 에러처리 handling, 성공시 id값 return
     */
    @GetMapping("/info")
    public ResponseEntity<?> info(@RequestParam String email){
        UserEntity userEntity = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("info RuntimeException"));

        return ResponseEntity.ok(new UserResponse(userEntity.getEmail()
                                                 ,userEntity.getName()
                                                 ,userEntity.getModifiedDt())
                                                 .toString());
    }

}