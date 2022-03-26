package account.controller;

import account.AccountServiceApplication;
import account.model.dto.AuthToken;
import account.model.dto.ChangePasswordRequest;
import account.model.jdbc.Account;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;
    private PasswordEncoder encoder;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> create(@Valid @RequestBody Account account) {
        ResponseEntity<?> validate = validatePassword(account.getPassword(), null, "/api/auth/signup");
        if (validate != null) {
            return validate;
        }

        if (accountService.get(account.getEmail()) != null) {
            return ResponseEntityUtil.getResponseEntity(Errors.USER_EXIST, "/api/auth/signup");
        }

        account = accountService.create(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    private ResponseEntity<?> validatePassword(String password, String oldPassword, String path) {
        if (password.length() < 12) {
            return ResponseEntityUtil.getResponseEntity(Errors.PASSWORD_SHORT, path);
        } else if (AccountServiceApplication.breachedPasswords.contains(password)) {
            return ResponseEntityUtil.getResponseEntity(Errors.PASSWORD_BRANCHED, path);
        } else if (oldPassword != null && encoder.matches(password, oldPassword)) {
            return ResponseEntityUtil.getResponseEntity(Errors.PASSWORD_MATCH, path);
        }
        return null;
    }

    @GetMapping(path = "/empl/payment")
    public ResponseEntity<?> get(@AuthenticationPrincipal AuthToken authToken) {
        Account account = accountService.get(authToken.getUsername());
        return account == null ?
                new ResponseEntity<>(HttpStatus.UNAUTHORIZED) :
                new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "/auth/changepass")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal AuthToken authToken, @RequestBody ChangePasswordRequest request) {
        ResponseEntity<?> validate = validatePassword(request.getPassword(), authToken.getPassword(), "/api/auth/changepass");
        if (validate != null) {
            return validate;
        }
        accountService.changePassword(authToken, request.getPassword());
        return new ResponseEntity<>(
                Map.of("email", authToken.getUsername().toLowerCase(Locale.ROOT),
                        "status", "The password has been updated successfully"
        ), HttpStatus.OK);
    }
}
