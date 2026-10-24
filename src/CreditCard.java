
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CreditCard extends Card {

    private final BigDecimal creditLimit;
    private BigDecimal remainingCredit;
    private BigDecimal transactionLimit =
            new BigDecimal("500.00");

    public CreditCard(String cardNumber,
                      LocalDate startDate,
                      LocalDate expiryDate,
                      BigDecimal creditLimit) {
        super(cardNumber, startDate, expiryDate);

        Objects.requireNonNull(creditLimit,
                "Credit limit cannot be null");

        if (creditLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Credit limit must be positive");
        }

        this.creditLimit = creditLimit;
        this.remainingCredit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getRemainingCredit() {
        return remainingCredit;
    }

    public BigDecimal getTransactionLimit() {
        return transactionLimit;
    }

    public BigDecimal getOutstandingBalance() {
        return creditLimit.subtract(remainingCredit);
    }

    public void setTransactionLimit(BigDecimal limit) {
        Objects.requireNonNull(limit,
                "Transaction limit cannot be null");

        if (limit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction limit must be positive");
        }

        transactionLimit = limit;
    }

    public boolean makePurchase(BigDecimal amount) {
        validatePositiveAmount(amount);

        if (!canTransact()) {
            return false;
        }

        if (amount.compareTo(transactionLimit) > 0) {
            return false;
        }

        if (amount.compareTo(remainingCredit) > 0) {
            return false;
        }

        remainingCredit = remainingCredit.subtract(amount);
        return true;
    }

    public boolean makeRepayment(BigDecimal amount) {
        validatePositiveAmount(amount);

        BigDecimal outstanding = getOutstandingBalance();

        if (amount.compareTo(outstanding) > 0) {
            return false;
        }

        remainingCredit = remainingCredit.add(amount);
        return true;
    }

    private static void validatePositiveAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }
    }
}
