package dall.e.api.finance_api.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank(message = "Account type is required")
    private String accountType;
    
    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal initialBalance;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String accountName;
}