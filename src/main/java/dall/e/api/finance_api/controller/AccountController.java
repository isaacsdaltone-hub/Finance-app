package dall.e.api.finance_api.controller;

import dall.e.api.finance_api.dto.AccountDto;
import dall.e.api.finance_api.dto.AccountRequest;
import dall.e.api.finance_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountRequest request) {
        log.info("Creating new account for user ID: {}", request.getUserId());
        AccountDto createdAccount = accountService.createAccount(request);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        log.info("Fetching account by ID: {}", id);
        Optional<AccountDto> account = accountService.getAccountById(id);
        return account.map(accountDTO -> ResponseEntity.ok(accountDTO))
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        log.info("Fetching all accounts");
        List<AccountDto> accounts = accountService.getAllAccounts(); // ✅ Correct type
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AccountDto>> getAllActiveAccounts() {
        log.info("Fetching all active accounts");
        List<AccountDto> accounts = accountService.getAllActiveAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable Long userId) {
        log.info("Fetching accounts for user ID: {}", userId);
        List<AccountDto> accounts = accountService.getAccountsByUserId(userId); // ✅ Correct type
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, 
                                                   @Valid @RequestBody AccountDto accountDTO) {
        log.info("Updating account with ID: {}", id);
        try {
            AccountDto updatedAccount = accountService.updateAccount(id, accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            log.error("Error updating account: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        log.info("Deleting account with ID: {}", id);
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting account: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long id) {
        log.info("Fetching balance for account ID: {}", id);
        try {
            BigDecimal balance = accountService.getAccountBalance(id);
            return ResponseEntity.ok(balance);
        } catch (RuntimeException e) {
            log.error("Error fetching balance: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateAccount(@PathVariable Long id) {
        log.info("Deactivating account with ID: {}", id);
        try {
            accountService.deactivateAccount(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error deactivating account: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateAccount(@PathVariable Long id) {
        log.info("Activating account with ID: {}", id);
        try {
            accountService.activateAccount(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error activating account: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}