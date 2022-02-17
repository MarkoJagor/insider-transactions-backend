package ee.taltech.insidertransactionsbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountDetailsServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username " + username));

        return AccountDetailsImpl.build(account);
    }
}
