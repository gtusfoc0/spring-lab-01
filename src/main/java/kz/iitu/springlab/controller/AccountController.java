package kz.iitu.springlab.controller;

import kz.iitu.springlab.account.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/lab4/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        return accountService.register(username, password);
    }
}