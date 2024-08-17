package vn.funix.FX38455.java.asm03.models;

import vn.funix.FX38455.java.asm02.models.Account;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction implements Serializable {
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;

    // Constructor
    public Transaction(String accountNumber, double amount, boolean status,String time) {
        this.id = UUID.randomUUID().toString();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.status = status;
    }



    public Transaction() {
        // Khởi tạo mặc định hoặc không làm gì cả
    }

    public Transaction(String accountNumber, double amount, boolean b, String format, String transfer) {
    }

    public void displayTranss() {

        DigitalCustomer digitalCustomer = new DigitalCustomer();
        DecimalFormat df = new DecimalFormat("#,### đ");

        System.out.print("[GD]  "+accountNumber+" |  ");
        System.out.print("-"+df.format(amount)+" | ");
        System.out.print(time + " | ");
        System.out.print(id + "  ");

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        System.out.println("Transaction ID: " + id);
//        System.out.println("Account Number: " + accountNumber);
//        System.out.println("Amount: " + amount);
//        System.out.println("Time: " + time.format(formatter));
//        System.out.println("Status: " + (status ? "Success" : "Failed"));
    }
    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
