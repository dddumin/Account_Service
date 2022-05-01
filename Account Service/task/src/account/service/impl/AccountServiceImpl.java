package account.service.impl;

import account.AccountServiceApplication;
import account.model.dto.AuthToken;
import account.model.dto.Result;
import account.model.jdbc.Account;
import account.repository.AccountRepository;
import account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static account.model.dto.HttpDescription.*;
import static account.model.dto.Result.error;
import static account.model.dto.Result.ok;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private AccountRepository accountRepo;
    private PasswordEncoder encoder;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Result<?> create(Account account, String path) {
        if (accountRepo.findByEmailIgnoreCase(account.getEmail()) != null) {
            return error(USER_EXIST, path);
        }
        Result<?> result = validatePassword(account.getPassword(), null, path);
        if (result != null) {
            return result;
        }
        account.setPassword(encoder.encode(account.getPassword()));
        accountRepo.save(account);
        return ok(account);
    }

    @Override
    public Result<Account> get(String email) {
        Account account = accountRepo.findByEmailIgnoreCase(email);
        if (account == null) {
            return error(UNAUTHORIZED);
        }
        return ok(account);
    }

    @Override
    public Result<?> changePassword(AuthToken authToken, String newPassword, String path) {
        Account account = accountRepo.findByEmailIgnoreCase(authToken.getUsername());
        Result<?> result = validatePassword(newPassword, account.getPassword(), path);
        if (result != null) {
            return result;
        }
        account.setPassword(encoder.encode(newPassword));
        accountRepo.save(account);
        Map<String, String> infos = Map.of(
                "email", account.getEmail(),
                "status", PASSWORD_SUCCESSFULLY_CHANGE.getMessage());
        return ok(infos);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new AuthToken(account);
    }


    private Result<?> validatePassword(String password, String oldPassword, String path) {
        if (password.length() < 12) {
            return error(PASSWORD_SHORT, path);
        } else if (AccountServiceApplication.getBranchedPasswords().contains(password)) {
            return error(PASSWORD_BRANCHED, path);
        } else if (oldPassword != null && encoder.matches(password, oldPassword)) {
            return error(PASSWORD_MATCH, path);
        }
        return null;
    }

}
