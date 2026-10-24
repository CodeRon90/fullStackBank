
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankAccount {

    private final int accountNumber;
    private final String accountType;
    private final Customer customer;
    private BigDecimal balance;
    private boolean active;
    private final List<DebitCard> debitCards = new ArrayList<>();

    public BankAccount(int accountNumber, String accountType,
                       BigDecimal openingBalance, Customer customer) {
        if (accountNumber <= 0) {
            throw new IllegalArgumentException(
                    "Account number must be positive");
        }

        if (accountType == null || accountType.isBlank()) {
            throw new IllegalArgumentException(
                    "Account type cannot be empty");
        }

        Objects.requireNonNull(openingBalance,
                "Opening balance cannot be null");
        Objects.requireNonNull(customer,
                "Customer cannot be null");

        if (openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative");
        }

        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = openingBalance;
        this.customer = customer;
        this.active = true;

        customer.addBankAccount(this);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccountHolderName() {
        return customer.getName();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void deposit(BigDecimal amount) {
        validatePositiveAmount(amount);
        ensureActive();

        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);
        ensureActive();

        if (amount.compareTo(balance) > 0) {
            throw new IllegalStateException(
                    "Insufficient funds");
        }

        balance = balance.subtract(amount);
    }

    public List<DebitCard> getDebitCards() {
        return List.copyOf(debitCards);
    }

    public void addDebitCard(DebitCard card) {
        Objects.requireNonNull(card, "Card cannot be null");

        if (card.getBankAccount() != this) {
            throw new IllegalArgumentException(
                    "Card is linked to a different account");
        }

        if (!debitCards.contains(card)) {
            debitCards.add(card);
        }
    }

    private void ensureActive() {
        if (!active) {
            throw new IllegalStateException(
                    "Bank account is not active");
        }
    }

    private static void validatePositiveAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }
    }
}
