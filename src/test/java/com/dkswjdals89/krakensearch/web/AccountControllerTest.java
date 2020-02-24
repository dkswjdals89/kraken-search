package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.service.account.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Account Controller 테스트")
public class AccountControllerTest {
    @MockBean
    AccountService accountService;

    MockMvc mockMvc;

    @Nested
    @DisplayName("계정 생성 요청 테스트")
    class CreateAccountRequest {
        @Test
        public void callCreateAccountService() {
        }
    }
}
