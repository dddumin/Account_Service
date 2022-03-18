package account.service;

import account.model.jdbc.Account;

public interface AccountService {
    Account create(Account account);
    Account get(String email);
}
