package ee.taltech.insidertransactionsbackend.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ee.taltech.insidertransactionsbackend.model.Account;

public class AccountDetailsImpl implements UserDetails {

    private final Long id;

    private final String username;

    private final String password;

    public AccountDetailsImpl(final Long id, final String username, final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static AccountDetailsImpl build(Account account) {
        return new AccountDetailsImpl(
                account.getAccountId(),
                account.getUsername(),
                account.getPassword()
        );
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public Long getId() {
        return this.id;
    }

    @Override public String getPassword() {
        return this.password;
    }

    @Override public String getUsername() {
        return this.username;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}
