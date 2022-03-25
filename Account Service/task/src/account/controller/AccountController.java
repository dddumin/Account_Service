package account.controller;

import account.model.exception.UserExistException;
import account.model.jdbc.Account;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> create(@Valid @RequestBody Account account) {
        Account acc = null;
        try {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            acc = accountService.create(account);
        } catch (UserExistException e) {
            return new ResponseEntity<Object>(
                    Map.of("timestamp", LocalDateTime.now(),
                            "status", 400,
                            "error", "Bad Request",
                            "message", "User exist!",
                            "path", "/api/auth/signup"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(acc, HttpStatus.OK);
    }

    @GetMapping(path = "/empl/payment")
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
        Account account = accountService.get(userDetails.getUsername());
        return account == null ?
                new ResponseEntity<>(HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(account, HttpStatus.OK);
    }
}
