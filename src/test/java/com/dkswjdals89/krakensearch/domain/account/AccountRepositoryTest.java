package com.dkswjdals89.krakensearch.domain.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void cleanup() {
        accountRepository.deleteAll();
    }

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
