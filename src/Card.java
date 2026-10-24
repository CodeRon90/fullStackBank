
import java.time.LocalDate;
import java.util.Objects;

public abstract class Card {

    private final String cardNumber;
    private final LocalDate startDate;
    private final LocalDate expiryDate;
    private boolean active;

    protected Card(String cardNumber,
                   LocalDate startDate,
                   LocalDate expiryDate) {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Card number cannot be empty");
        }

        Objects.requireNonNull(startDate,
                "Start date cannot be null");
        Objects.requireNonNull(expiryDate,
                "Expiry date cannot be null");

        if (expiryDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Expiry date cannot be before start date");
        }

        this.cardNumber = cardNumber;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.active = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        if (isExpired()) {
            throw new IllegalStateException(
                    "An expired card cannot be activated");
        }
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    protected boolean canTransact() {
        LocalDate today = LocalDate.now();

        return active
                && !today.isBefore(startDate)
                && !today.isAfter(expiryDate);
    }
}
