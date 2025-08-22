package dall.e.api.finance_api.repository;

import dall.e.api.finance_api.entity.Transaction;
import dall.e.api.finance_api.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAccountId(Long accountId);
    List<Transaction> findByTransactionType(TransactionType transactionType);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = ?1 AND t.deletedAt IS NULL ORDER BY t.createdAt DESC")
    List<Transaction> findActiveTransactionsByAccountId(Long accountId);
}