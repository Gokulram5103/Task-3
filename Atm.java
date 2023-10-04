import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankTransaction {
    private String type;
    private double amount;

    public BankTransaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private String userId;
    private String userPin;
    private double balance;
    private List<BankTransaction> transactionHistory;

    public BankAccount(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addToTransactionHistory(String type, double amount) {
        BankTransaction transaction = new BankTransaction(type, amount);
        transactionHistory.add(transaction);
    }

    public List<BankTransaction> getTransactionHistory() {
        return transactionHistory;
    }
}

public class Atm{

    private static BankAccount currentUser;

    public static void main(String[] args) {
        BankAccount user = new BankAccount("ABCDE", "54321", 1500.0);
        currentUser = user;
        startATM();
    }

    public static void startATM() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Unique ATM!");
            System.out.println("1. View Transactions");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdrawFunds();
                    break;
                case 3:
                    depositFunds();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    System.out.println("Thank you for using the Unique ATM!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showTransactionHistory() {
        List<BankTransaction> transactions = currentUser.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (BankTransaction transaction : transactions) {
                System.out.println(transaction.getType() + ": $" + transaction.getAmount());
            }
        }
    }

    public static void withdrawFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            currentUser.addToTransactionHistory("Withdrawal", amount);
            System.out.println("Withdrawal successful. Remaining balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    public static void depositFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            currentUser.addToTransactionHistory("Deposit", amount);
            System.out.println("Deposit successful. New balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public static void transferFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the recipient's user ID: ");
        String recipientId = scanner.next();

        // Assuming you have a list of users, you can search for the recipient using their user ID

        // For demo purposes, let's assume we found the recipient
        BankAccount recipient = new BankAccount("FGHIJ", "67890", 800.0);

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            currentUser.addToTransactionHistory("Transfer to " + recipientId, amount);
            recipient.addToTransactionHistory("Received from " + currentUser.getUserId(), amount);
            System.out.println("Transfer successful. Remaining balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }
}
