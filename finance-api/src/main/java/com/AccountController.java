package com.mayse.financeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AppState appState;

    @GetMapping
    public List<Account> getAllAccounts() {
        return appState.getUser().getAccounts();
    }

    @PostMapping("/{index}/deposit")
    public Account deposit(@PathVariable int index, @RequestBody Map<String, Double> body) {
        Account account = appState.getUser().getAccounts().get(index);
        account.deposit(body.get("amount"));
        return account;
    }
}