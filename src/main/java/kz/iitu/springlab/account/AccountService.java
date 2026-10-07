package kz.iitu.springlab.account;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public String register(String username, @Sensitive String password) {
        return "User " + username + " registered";
    }
}