package ee.taltech.insidertransactionsbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.taltech.insidertransactionsbackend.model.Transaction;
import ee.taltech.insidertransactionsbackend.repository.TransactionRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("http://localhost:3000/")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedDate"));
    }
}
