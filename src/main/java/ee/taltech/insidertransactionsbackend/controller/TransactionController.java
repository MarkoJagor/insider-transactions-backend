package ee.taltech.insidertransactionsbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.taltech.insidertransactionsbackend.exception.ResourceNotFoundException;
import ee.taltech.insidertransactionsbackend.model.Transaction;
import ee.taltech.insidertransactionsbackend.repository.TransactionRepository;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedDate"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id %s does not exist", id)));
        return ResponseEntity.ok(transaction);
    }
}
