package vn.funix.FX38455.java.asm04.models;
import vn.funix.FX38455.java.asm04.models.TransactionType;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

public class Transaction implements Serializable {
        private static final long serialVersionUID = 1L;

    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    private String type; // Thêm thuộc tính type để đánh dấu loại giao dịch

    // Constructor
    public Transaction(String accountNumber, double amount, boolean status, String time, String type) {
        this.id = UUID.randomUUID().toString();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.status = status;
        this.type = type;
    }

    public void displayTranss() {
        DecimalFormat df = new DecimalFormat("#,### đ");

        System.out.print("[GD]  " + accountNumber + " |  ");

        // Chuyển đổi chuỗi type sang enum TransactionType
        TransactionType transactionType = TransactionType.valueOf(type);

        System.out.print(type + "    |"); // Hiển thị loại giao dịch

        // Kiểm tra loại giao dịch và hiển thị dấu "+" hoặc "-"
        if (transactionType == TransactionType.DEPOSIT) {
            System.out.print(" +" + df.format(amount) + " | ");
        } else {
            System.out.print(" -" + df.format(amount) + " | ");
        }

        System.out.println(time + " | ");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
