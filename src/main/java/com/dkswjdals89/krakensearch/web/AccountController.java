package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.dto.account.AccountSignUpRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninResponseDto;
import com.dkswjdals89.krakensearch.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signup(@RequestBody @Valid AccountSignUpRequestDto requestDto) {
        accountService.signup(requestDto);
    }

    @PostMapping("/signin")
    public AccountSigninResponseDto signin(@RequestBody @Valid AccountSigninRequestDto requestDto) {
        return accountService.signin(requestDto);
    }
}
