package account.controller;

import account.model.dto.AuthToken;
import account.model.dto.Result;
import account.model.jdbc.Account;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> create(@Valid @RequestBody Account account) {
        Result<?> result = accountService.create(account, "/api/auth/signup");
        return ResponseEntity.status(result.getHttpStatus()).body(result.getObj());
    }

    @GetMapping(path = "/empl/payment")
    public ResponseEntity<?> get(@AuthenticationPrincipal AuthToken authToken) {
        Result<Account> result = accountService.get(authToken.getUsername());
        return ResponseEntity.status(result.getHttpStatus()).body(result.getObj());
    }

    @PostMapping(path = "/auth/changepass")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal AuthToken authToken, @RequestBody String new_password) {
        Result<?> result = accountService.changePassword(authToken, new_password, "/api/auth/changepass");
        return ResponseEntity.status(result.getHttpStatus()).body(result.getObj());
    }
}
