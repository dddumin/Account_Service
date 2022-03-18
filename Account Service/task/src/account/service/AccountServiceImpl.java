package account.service;

import account.model.exception.UserExistException;
import account.model.jdbc.Account;
import account.model.dto.AuthToken;
import account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private AccountRepository accountRepo;

    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account create(Account account) {
        Account accountByEmail = accountRepo.findByEmailIgnoreCase(account.getEmail());
        if (accountByEmail != null) {
            throw new UserExistException();
        }
        return accountRepo.save(account);
    }

    @Override
    public Account get(String email) {
        return accountRepo.findByEmailIgnoreCase(email);
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
