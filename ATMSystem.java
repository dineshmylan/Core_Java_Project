package StartProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class Account {
    String accountNumber;
    String pin;
    double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }
}

class ATM {
    HashMap<String, Account> accounts = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public ATM() {
        // Preloaded accounts for simulation
        accounts.put("12345", new Account("12345", "1111", 5000));
        accounts.put("67890", new Account("67890", "2222", 10000));
    }

    public void start() {
        System.out.println("=== Welcome to ATM ===");
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        Account acc = accounts.get(accNo);

        if (!acc.authenticate(pin)) {
            System.out.println("Invalid PIN!");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: ₹" + acc.getBalance());
                    logTransaction(accNo, "Checked Balance: ₹" + acc.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmt = sc.nextDouble();
                    acc.deposit(depositAmt);
                    System.out.println("Deposit Successful!");
                    logTransaction(accNo, "Deposited: ₹" + depositAmt);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmt = sc.nextDouble();
                    if (acc.withdraw(withdrawAmt)) {
                        System.out.println("Withdrawal Successful!");
                        logTransaction(accNo, "Withdrew: ₹" + withdrawAmt);
                    } else {
                        System.out.println("Insufficient Balance!");
                        logTransaction(accNo, "Failed Withdrawal Attempt: ₹" + withdrawAmt);
                    }
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private void logTransaction(String accountNumber, String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ATM_Transactions.txt", true))) {
            writer.write("Account: " + accountNumber + " - " + action);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction log: " + e.getMessage());
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
