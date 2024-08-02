package org.zzzang.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1() throws Exception {
        mockMvc.perform(post("/member/join")
                        .with(csrf().asHeader())
                .param("email", "user01@test.org"))
                .andDo(print());
    }

    @Test
    @WithMockUser // 실제로 인증 과정을 거치지 않고도 특정 사용자 권한을 가진 상태에서 테스트를 수행
    void test2() throws Exception {
        mockMvc.perform(get("/mypage"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user01@test.org", authorities = "ADMIN")
    void test3() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print());
    }
}
