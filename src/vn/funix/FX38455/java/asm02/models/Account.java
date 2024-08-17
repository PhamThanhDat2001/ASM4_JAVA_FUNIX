package vn.funix.FX38455.java.asm02.models;

import vn.funix.FX38455.java.asm03.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private double balance;

    //
    private List<Transaction> transactions = new ArrayList<>();

   //as04
//   public Account(String accountNumber, double balance) {
//       this.accountNumber = accountNumber;
//       this.balance = balance;
//
//   }
    //
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    //
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;
    }

    public boolean isPremiumAccount() {
        return this.balance >= 10_000_000;
    }

    @Override
    public String toString() {
        return accountNumber + "|                      " + balance + 'đ';
    }

    //as4
    // Phương thức rút tiền từ tài khoản
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Số tiền rút phải lớn hơn 0.");
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Số dư không đủ để rút.");
            return false;
        }
    }

    // Phương thức chuyển tiền từ tài khoản nguồn sang tài khoản đích
    public boolean transfer(Account destinationAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Số tiền chuyển phải lớn hơn 0.");
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            destinationAccount.deposit(amount);
            return true;
        } else {
            System.out.println("Số dư không đủ để chuyển.");
            return false;
        }
    }

    // Phương thức nạp tiền vào tài khoản
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Số tiền nạp phải lớn hơn 0.");
        } else {
            balance += amount;
        }
    }
}
////////////////