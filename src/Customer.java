
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private final int customerID;
    private String name;
    private String email;
    private String phoneNumber;
    private final List<BankAccount> bankAccounts = new ArrayList<>();

    public Customer(int customerID, String name,
                    String email, String phoneNumber) {
        if (customerID <= 0) {
            throw new IllegalArgumentException(
                    "Customer ID must be positive");
        }

        this.customerID = customerID;
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()
                || !email.contains("@")) {
            throw new IllegalArgumentException(
                    "A valid email address is required");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    public void updateEmail(String newEmail) {
        setEmail(newEmail);
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        setPhoneNumber(newPhoneNumber);
    }

    public List<BankAccount> getBankAccounts() {
        return List.copyOf(bankAccounts);
    }

    public void addBankAccount(BankAccount account) {
        Objects.requireNonNull(account, "Account cannot be null");

        if (account.getCustomer() != this) {
            throw new IllegalArgumentException(
                    "Account belongs to a different customer");
        }

        if (!bankAccounts.contains(account)) {
            bankAccounts.add(account);
        }
    }

    public void removeBankAccount(BankAccount account) {
        Objects.requireNonNull(account, "Account cannot be null");

        if (account.getCustomer() == this) {
            throw new IllegalStateException(
                    "Unlink the account from its customer first");
        }

        bankAccounts.remove(account);
    }
}
