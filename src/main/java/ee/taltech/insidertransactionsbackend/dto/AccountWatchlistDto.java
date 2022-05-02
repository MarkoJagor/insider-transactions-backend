package ee.taltech.insidertransactionsbackend.dto;

import java.util.Set;

import ee.taltech.insidertransactionsbackend.model.Issuer;

public class AccountWatchlistDto {

    private Set<Issuer> issuers;

    private boolean alphaReturns;

    public Set<Issuer> getIssuers() {
        return this.issuers;
    }

    public void setIssuers(final Set<Issuer> issuers) {
        this.issuers = issuers;
    }

    public boolean isAlphaReturns() {
        return this.alphaReturns;
    }

    public void setAlphaReturns(final boolean alphaReturns) {
        this.alphaReturns = alphaReturns;
    }
}
