package account.controller;

import account.model.dto.AuthToken;
import account.model.jdbc.Account;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
        return new ResponseEntity<>(accountService.create(account), HttpStatus.OK);
    }

    @GetMapping(path = "/empl/payment")
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetails userDetails) {
        Account account = accountService.get(userDetails.getUsername());
        return account == null ?
                new ResponseEntity<>(HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(account, HttpStatus.OK);
    }
}
