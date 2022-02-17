package ee.taltech.insidertransactionsbackend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "account", schema = "transactions")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "account_issuer",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "issuer_id") })
    private Set<Issuer> issuers = new HashSet<>();

    public Account(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public Account() {

    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Issuer> getIssuers() {
        return this.issuers;
    }

    public void setIssuers(final Set<Issuer> issuers) {
        this.issuers = issuers;
    }
}
