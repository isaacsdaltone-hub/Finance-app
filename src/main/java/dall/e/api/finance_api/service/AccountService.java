package dall.e.api.finance_api.service;

import dall.e.api.finance_api.dto.AccountDto;
import dall.e.api.finance_api.dto.AccountRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    // Create a new account from a request DTO
    AccountDto createAccount(AccountRequest request);

    // Retrieve an account by its ID
    Optional<AccountDto> getAccountById(Long accountId);

    // Retrieve all accounts for a specific user
    List<AccountDto> getAccountsByUserId(Long userId);

    // Retrieve all accounts
    List<AccountDto> getAllAccounts();

    // Retrieve all active accounts
    List<AccountDto> getAllActiveAccounts();

    // Update an existing account by ID using a DTO
    AccountDto updateAccount(Long accountId, AccountDto accountDto);

    // Soft-delete an account (sets deletedAt)
    void deleteAccount(Long accountId);

    // Get the current balance of an account
    BigDecimal getAccountBalance(Long accountId);

    // Deactivate an account (sets isActive = false)
    void deactivateAccount(Long accountId);

    // Activate an account (sets isActive = true)
    void activateAccount(Long accountId);
}
