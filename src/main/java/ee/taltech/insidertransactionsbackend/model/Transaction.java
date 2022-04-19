package ee.taltech.insidertransactionsbackend.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction", schema = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "trade_date")
    private Date tradeDate;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "investor")
    private String investor;

    @Column(name = "investor_position")
    private String investorPosition;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "instrument")
    private String instrument;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "price")
    private Double price;

    @Column(name = "market")
    private String market;

    @Column(name = "has_been_updated")
    private Boolean hasBeenUpdated;

    @Column(name = "update_reason")
    private String updateReason;

    public Transaction() {
    }

    public Transaction(final Date tradeDate, final Date publishedDate, final String investor, final String investorPosition,
            final String issuer, final String instrument, final String transactionType,
            final Double volume, final Double price, final String market, final Boolean hasBeenUpdated, final String updateReason) {
        this.tradeDate = tradeDate;
        this.publishedDate = publishedDate;
        this.investor = investor;
        this.investorPosition = investorPosition;
        this.issuer = issuer;
        this.instrument = instrument;
        this.transactionType = transactionType;
        this.volume = volume;
        this.price = price;
        this.market = market;
        this.hasBeenUpdated = hasBeenUpdated;
        this.updateReason = updateReason;
    }

    public Long getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(final Long transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTradeDate() {
        return this.tradeDate;
    }

    public void setTradeDate(final Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(final Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getInvestor() {
        return this.investor;
    }

    public void setInvestor(final String investor) {
        this.investor = investor;
    }

    public String getInvestorPosition() {
        return this.investorPosition;
    }

    public void setInvestorPosition(final String investorPosition) {
        this.investorPosition = investorPosition;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    public String getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final String instrument) {
        this.instrument = instrument;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getVolume() {
        return this.volume;
    }

    public void setVolume(final Double volume) {
        this.volume = volume;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String getMarket() {
        return this.market;
    }

    public void setMarket(final String market) {
        this.market = market;
    }

    public boolean isHasBeenUpdated() {
        return this.hasBeenUpdated;
    }

    public void setHasBeenUpdated(final boolean hasBeenUpdated) {
        this.hasBeenUpdated = hasBeenUpdated;
    }

    public String getUpdateReason() {
        return this.updateReason;
    }

    public void setUpdateReason(final String updateReason) {
        this.updateReason = updateReason;
    }
}
