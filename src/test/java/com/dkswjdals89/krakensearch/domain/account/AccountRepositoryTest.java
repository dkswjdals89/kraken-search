package com.dkswjdals89.krakensearch.domain.account;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @After
    public void cleanup() {
        accountRepository.deleteAll();
    }

    @Test
    public void insertTest() {
        // Given
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        accountRepository.save(Account.builder()
                .userId(userId)
                .password(password)
                .build());

        // When
        List<Account> postsList = accountRepository.findAll();

        // Then
        Account account = postsList.get(0);
        assertThat(account.getUserId()).isEqualTo(userId);
        assertThat(account.getPassword()).isEqualTo(password);
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
        Account findAccount = accountRepository.findOneByUserId(userId);

        // Then
        assertThat(findAccount.getUserId()).isEqualTo(userId);
    }
}
