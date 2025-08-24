package dall.e.api.finance_api.service;

import dall.e.api.finance_api.dto.AccountDto;
import dall.e.api.finance_api.dto.AccountRequest;
import dall.e.api.finance_api.entity.Account;
import dall.e.api.finance_api.entity.User;
import dall.e.api.finance_api.repository.AccountRepository;
import dall.e.api.finance_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public AccountDto createAccount(AccountRequest request) {
        log.info("Creating account for user ID: {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialBalance());
        account.setCurrency(request.getCurrency());
        account.setUser(user);
        account.setAccountName(request.getAccountName());
        account.setActive(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);
        log.info("Account created successfully with ID: {}", savedAccount.getAccountId());

        return convertToDTO(savedAccount);
    }

    @Override
    @Cacheable(value = "accounts", key = "#id")
    public Optional<AccountDto> getAccountById(Long id) {
        log.info("Fetching account by ID: {}", id);
        return accountRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        log.info("Fetching accounts for user ID: {}", userId);
        return accountRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        log.info("Fetching all accounts");
        return accountRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() {
        log.info("Fetching all active accounts");
        return accountRepository.findAllActiveAccounts()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "accounts", key = "#id")
    public AccountDto updateAccount(Long id, AccountDto accountDTO) {
        log.info("Updating account with ID: {}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));

        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getBalance());
        account.setCurrency(accountDTO.getCurrency());
        account.setAccountName(accountDTO.getAccountName());
        account.setActive(accountDTO.isActive());
        account.setUpdatedAt(LocalDateTime.now());

        Account updatedAccount = accountRepository.save(account);
        log.info("Account updated successfully with ID: {}", updatedAccount.getAccountId());

        return convertToDTO(updatedAccount);
    }

    @Override
    @CacheEvict(value = "accounts", key = "#id")
    public void deleteAccount(Long id) {
        log.info("Soft-deleting account with ID: {}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));

        account.setDeletedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        log.info("Account soft-deleted successfully with ID: {}", id);
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        log.info("Fetching balance for account ID: {}", accountId);
        return accountRepository.findById(accountId)
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
    }

    @Override
    @CacheEvict(value = "accounts", key = "#accountId")
    public void deactivateAccount(Long accountId) {
        log.info("Deactivating account with ID: {}", accountId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));

        account.setActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        log.info("Account deactivated successfully with ID: {}", accountId);
    }

    @Override
    @CacheEvict(value = "accounts", key = "#accountId")
    public void activateAccount(Long accountId) {
        log.info("Activating account with ID: {}", accountId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));

        account.setActive(true);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        log.info("Account activated successfully with ID: {}", accountId);
    }

    // Helper methods
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private AccountDto convertToDTO(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getAccountId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setUserId(account.getUser().getUserId());
        dto.setAccountName(account.getAccountName());
        dto.setActive(account.isActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        return dto;
    }
}
