package account.service;

import account.model.dto.AuthToken;
import account.model.dto.Result;
import account.model.jdbc.Account;

public interface AccountService {
    Result<?> create(Account account, String path);
    Result<Account> get(String email);
    Result<?> changePassword(AuthToken authToken, String newPassword, String path);
}
