package dall.e.api.finance_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;  // maps to accountId in entity
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotBlank(message = "Account type is required")
    private String accountType; // SAVINGS, CHECKING, CREDIT, etc. (ensure entity has this field)
    
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal balance;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotNull(message = "User ID is required")
    private Long userId;  // maps to user.userId in entity
    
    private String accountName;
    
    private boolean isActive;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private LocalDateTime deletedAt;  // track soft delete
}
