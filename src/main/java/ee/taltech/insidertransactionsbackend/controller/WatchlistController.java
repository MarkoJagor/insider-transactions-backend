package ee.taltech.insidertransactionsbackend.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.taltech.insidertransactionsbackend.exception.ResourceNotFoundException;
import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.model.Issuer;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;
import ee.taltech.insidertransactionsbackend.repository.IssuerRepository;

@RestController
@RequestMapping("/api/v1/watchlist")
@CrossOrigin("http://localhost:3000/")
public class WatchlistController {

    private final IssuerRepository issuerRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public WatchlistController(final IssuerRepository issuerRepository, final AccountRepository accountRepository) {
        this.issuerRepository = issuerRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/issuers")
    public List<Issuer> getActiveIssuers() {
        return this.issuerRepository.findByIsActiveTrue();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Set<Issuer>> getAccountIssuers(@PathVariable Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id %s does not exist", id)));

        return ResponseEntity.ok(account.getIssuers());
    }

    @PutMapping("/account/{id}")
    public void updateAccountIssuers(@RequestBody Set<Issuer> issuerSet, @PathVariable Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id %s does not exist", id)));
        account.setIssuers(issuerSet);
        this.accountRepository.save(account);
    }
}
