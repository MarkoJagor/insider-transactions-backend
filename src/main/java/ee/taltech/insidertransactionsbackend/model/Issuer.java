package ee.taltech.insidertransactionsbackend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "issuer", schema = "transactions")
public class Issuer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuer_id")
    private Long issuerId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "issuers")
    @JsonIgnore
    private Set<Account> accounts = new HashSet<>();

    public Issuer(final String name) {
        this.name = name;
    }

    public Issuer() {

    }

    public Long getIssuerId() {
        return this.issuerId;
    }

    public void setIssuerId(final Long issuerId) {
        this.issuerId = issuerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }

    public Set<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(final Set<Account> accounts) {
        this.accounts = accounts;
    }
}
