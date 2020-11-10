package com.exam.BithumbTest.controller;

import com.exam.BithumbTest.dto.UserRequest;
import com.exam.BithumbTest.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserControllerTest {
    private static MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private MockMvc mockMvc;



    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void 회원가입_유효성실패() throws Exception {
        UserRequest userRequest = UserRequest.builder().email("wnqlsdl63naver.com") // 이메일 유효성 실패
                .name("wnqls")
                .password("wnqlsdl12345!@#$%")
                .build();


        mockMvc.perform(post("/v1/member/join")
                .contentType(JSON_CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void 회원가입_정상() throws Exception {
        UserRequest userRequest = UserRequest.builder().email("wnqlsdl63@naver.com")
                                                       .name("wnqls")
                                                       .password("wnqlsdl12345!@#$%")
                                                       .build();


        mockMvc.perform(post("/v1/member/join")
                .contentType(JSON_CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }

    @Test
    public void JWT_Token검증() throws Exception {

        mockMvc.perform(get("/v1/member/info"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 로그인_JWT_Token_생성() throws Exception {
        String token = create_token();

        assertNotNull(token);

        mockMvc.perform(post("/v1/member/login")
                .header(JwtTokenUtil.HEADER, token))
                .andExpect(status().isOk())
                .andDo(print());;

    }

    public String create_token() {
        return jwtTokenUtil.generateToken("wnqlsdl63@naver.com");
    }

}