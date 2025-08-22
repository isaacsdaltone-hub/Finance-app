package dall.e.api.finance_api.service;

import dall.e.api.finance_api.dto.TransactionDto;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto);
    Optional<TransactionDto> getTransactionById(Long id);
    List<TransactionDto> getTransactionsByAccountId(Long accountId);
    List<TransactionDto> getAllTransactions();
    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);
    void deleteTransaction(Long id);
}
