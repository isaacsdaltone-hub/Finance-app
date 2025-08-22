package dall.e.api.finance_api.service;

import dall.e.api.finance_api.dto.TransactionDto;
import dall.e.api.finance_api.entity.Transaction;
import dall.e.api.finance_api.repository.TransactionRepository;
import dall.e.api.finance_api.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = convertToEntity(transactionDto);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    @Override
    public Optional<TransactionDto> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setAmount(transactionDto.getAmount());
        existing.setTransactionType(transactionDto.getTransactionType());
        existing.setCategory(transactionDto.getCategory());
        existing.setDescription(transactionDto.getDescription());
        existing.setAccountId(transactionDto.getAccountId());
        existing.setReferenceNumber(transactionDto.getReferenceNumber());
        existing.setTransactionDate(transactionDto.getTransactionDate());

        Transaction updated = transactionRepository.save(existing);
        return convertToDto(updated);
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    // -----------------------
    // Mapping Helpers
    // -----------------------

    private TransactionDto convertToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getAccountId(),
                transaction.getReferenceNumber(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }

    private Transaction convertToEntity(TransactionDto dto) {
        return new Transaction(
                dto.getId(),
                dto.getAmount(),
                dto.getTransactionType(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getAccountId(),
                dto.getReferenceNumber(),
                dto.getTransactionDate(),
                dto.getCreatedAt()
        );
    }
}
