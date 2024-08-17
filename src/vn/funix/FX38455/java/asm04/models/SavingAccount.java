package vn.funix.FX38455.java.asm04.models;

import vn.funix.FX38455.java.asm04.models.Transaction;
import vn.funix.FX38455.java.asm04.models.TransactionType;
import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.service.IReport;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class SavingAccount extends Account implements IReport {

    public static final int SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));

    public SavingAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public SavingAccount() {
    }

    public SavingAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId);
    }

    //    LocalDateTime time = LocalDateTime.parse(LocalDateTime.now().format(formatter));
//    @Override
    public  void log(double amount, TransactionType transactionType, Account receiveAccount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.println("+---------+-----------------------+---------+");
        System.out.println("BIEN LAI GIAO DICH SAVINGS");
        System.out.println("NGAY G/D:          " + LocalDateTime.now().format(formatter));
        System.out.println("ATM ID:            DIGITAL-BANK-ATM 2024");
        System.out.println("SO TK:             " + getAccountNumber());
        System.out.println("SO TIEN:           " + df.format(amount));
        System.out.println("SO DU:             " + df.format(getBalance()));
        System.out.println("PHI + VAT:         0đ" );
        System.out.println("LOAI GIAO DICH:    " + transactionType);
        if (transactionType == TransactionType.TRANSFER) {
            System.out.println("TAI KHOAN NHAN:    " + receiveAccount.getAccountNumber());
        }
        System.out.println("+---------+-----------------------+---------+");
    }


    // Phương thức rút tiền từ tài khoản
    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            if (getBalance() >= amount && getBalance() - amount >= 50_000) {
                setBalance(getBalance() - amount);
                return true;
            } else {
                System.out.println("Số tiền trong tài khoản không đủ.");
            }
        } else {
            System.out.println("Không đủ điều kiện rút tiền.");
        }
        return false;
    }

    public boolean isAccepted(double amount) {
        if (isPremiumAccount()) {
            return amount > 50_000 && amount % 10_000 == 0;
        } else {
            return amount > 50_000 && amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW && amount % 10_000 == 0;
        }
    }


}
