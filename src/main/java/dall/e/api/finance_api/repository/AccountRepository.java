package dall.e.api.finance_api.repository;

import dall.e.api.finance_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByUserUserId(Long userId);
    
    @Query("SELECT a FROM Account a WHERE a.user.userId = ?1 AND a.deletedAt IS NULL")
    List<Account> findActiveAccountsByUserId(Long userId);
}