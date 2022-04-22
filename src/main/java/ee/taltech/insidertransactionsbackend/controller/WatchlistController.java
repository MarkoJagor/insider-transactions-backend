package ee.taltech.insidertransactionsbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.taltech.insidertransactionsbackend.dto.AccountWatchlist;
import ee.taltech.insidertransactionsbackend.exception.ResourceNotFoundException;
import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.model.Issuer;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;
import ee.taltech.insidertransactionsbackend.repository.IssuerRepository;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {

    private final IssuerRepository issuerRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public WatchlistController(final IssuerRepository issuerRepository, final AccountRepository accountRepository) {
        this.issuerRepository = issuerRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/issuers")
    public ResponseEntity<List<Issuer>> getActiveIssuers() {
        List<Issuer> activeIssuers = this.issuerRepository.findByIsActiveTrue();
        return ResponseEntity.ok(activeIssuers);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountWatchlist> getAccountWatchlist(@PathVariable Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id %s does not exist", id)));

        AccountWatchlist accountWatchlist = new AccountWatchlist();
        accountWatchlist.setIssuers(account.getIssuers());
        accountWatchlist.setAlphaReturns(account.isAlphaReturns());

        return ResponseEntity.ok(accountWatchlist);
    }

    @PutMapping("/account/{id}")
    public void updateAccountWatchlist(@RequestBody AccountWatchlist accountWatchlist, @PathVariable Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id %s does not exist", id)));
        account.setIssuers(accountWatchlist.getIssuers());
        account.setAlphaReturns(accountWatchlist.isAlphaReturns());
        this.accountRepository.save(account);
    }
}
