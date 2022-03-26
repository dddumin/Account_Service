package account.service;

import account.model.dto.AuthToken;
import account.model.jdbc.Account;
import account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Account create(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        return accountRepo.save(account);
    }
    @Override
    public Account get(String email) {
        return accountRepo.findByEmailIgnoreCase(email);
    }

    @Override
    public void changePassword(AuthToken authToken, String newPassword) {
        Account account = get(authToken.getUsername());
        account.setPassword(encoder.encode(newPassword));
        accountRepo.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByEmailIgnoreCase(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new AuthToken(account);
    }
}
