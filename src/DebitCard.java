
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class DebitCard extends Card {

    private final BankAccount bankAccount;
    private BigDecimal withdrawalLimit =
            new BigDecimal("500.00");

    public DebitCard(String cardNumber,
                     LocalDate startDate,
                     LocalDate expiryDate,
                     BankAccount bankAccount) {
        super(cardNumber, startDate, expiryDate);

        this.bankAccount = Objects.requireNonNull(
                bankAccount, "Bank account cannot be null");

        bankAccount.addDebitCard(this);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public BigDecimal getBalance() {
        return bankAccount.getBalance();
    }

    public BigDecimal getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(BigDecimal limit) {
        Objects.requireNonNull(limit,
                "Withdrawal limit cannot be null");

        if (limit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal limit must be positive");
        }

        withdrawalLimit = limit;
    }

    public void deposit(BigDecimal amount) {
        ensureCardUsable();
        bankAccount.deposit(amount);
    }

    public void withdraw(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        ensureCardUsable();

        if (amount.compareTo(withdrawalLimit) > 0) {
            throw new IllegalArgumentException(
                    "Withdrawal limit exceeded");
        }

        bankAccount.withdraw(amount);
    }

    private void ensureCardUsable() {
        if (!canTransact()) {
            throw new IllegalStateException(
                    "Card is inactive, not yet valid, or expired");
        }
    }
}
