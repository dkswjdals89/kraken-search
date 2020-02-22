package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.service.account.AccountService;
import com.dkswjdals89.krakensearch.dto.account.AccountCreateRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public AccountDetailDto createAccount(@RequestBody @Valid AccountCreateRequestDto requestDto) throws Exception {
        return accountService.joinAccount(requestDto);
    }
}
