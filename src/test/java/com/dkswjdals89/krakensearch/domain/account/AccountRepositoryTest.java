package com.dkswjdals89.krakensearch.domain.account;

import jdk.internal.jline.internal.Nullable;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    public void cleanup() {
        accountRepository.deleteAll();
    }

    @Test
    public void insertTest() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        Account createdAccount = accountRepository.save(Account.builder()
                .userId(userId)
                .password(password)
                .build());

        Optional<Account> account = accountRepository.findById(createdAccount.getId());

        assertThat(account).isNotNull();
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
        assertThat(findAccount.isPresent()).isFalse();
        assertThat(findAccount.get().getUserId()).isEqualTo(userId);
    }
}
