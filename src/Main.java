//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    Customer craig = new Customer(332332,"craig","craig","38928329");

   BankAccount one = new BankAccount(2121,"craig",0,"debit",false);


   craig.addBankAccount(one);
        System.out.println(one.getCustomer().getName());
        }
    }
