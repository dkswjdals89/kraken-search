package com.dkswjdals89.krakensearch.domain.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("계정 Repository 테스트")
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void cleanup() {
        accountRepository.deleteAll();
    }

    @Nested
    @DisplayName("계정 생성 테스트")
    class CreateAccountTest {
        @Test
        @DisplayName("Account 데이터 생성 테스트")
        public void insertTest() {
            String userId = "dkswjdals89";
            String password = "dkswjdals89!@";

            Account createdAccount = accountRepository.save(Account.builder()
                    .userId(userId)
                    .password(password)
                    .build());

            Optional<Account> account = accountRepository.findById(createdAccount.getId());

            assertThat(account, notNullValue());
        }

        @Test
        @DisplayName("이미 해당 userId로 등록된 계정이 존재하는 경우, 오류를 반환해야 한다.")
        public void insertDuplicateTest() {
            String userId = "dkswjdals89";
            String password = "dkswjdals89!@";

            accountRepository.save(Account.builder()
                    .userId(userId)
                    .password(password)
                    .build());
            DataAccessException exception = assertThrows(DataAccessException.class, () -> accountRepository.save(Account.builder()
                    .userId(userId)
                    .password(password)
                    .build()));

            assertThat(exception, notNullValue());
        }
    }

    @Nested
    @DisplayName("계정 아이디 조회 테스트")
    class FindAccountByUserIdTest {
        @Test
        public void findOneByUserIdTest() {
            // Given
            String userId = "dkswjdals89";
            String password = "dkswjdals89!@";

            accountRepository.save(Account.builder()
                    .userId(userId)
                    .password(password)
                    .build());

            // When
            Optional<Account> findAccount = accountRepository.findOneByUserId(userId);

            // Then
            assertThat(findAccount.isPresent(), equalTo(true));
            assertThat(findAccount.get().getUserId(), equalTo(userId));
        }
    }
}
