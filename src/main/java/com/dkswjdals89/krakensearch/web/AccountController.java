package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.dto.account.AccountSigninRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninResponseDto;
import com.dkswjdals89.krakensearch.service.account.AccountService;
import com.dkswjdals89.krakensearch.dto.account.AccountSignUpRequestDto;
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

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid AccountSignUpRequestDto requestDto) {
        accountService.signup(requestDto);
    }

    @PostMapping("/signin")
    public AccountSigninResponseDto signin(@RequestBody @Valid AccountSigninRequestDto requestDto) {
        return accountService.signin(requestDto);
    }
}
