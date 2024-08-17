package vn.funix.FX38455.java.asm03.models;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Bank;
import vn.funix.FX38455.java.asm02.models.Customer;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class LoanAccount extends Account implements ReportService,Withdraw{
    public static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    public static final int LOAN_ACCOUNT_MAX_BALANCE = 100_000_000;
    public static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
//    LocalDateTime time = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    @Override
    public void log(double amount) {
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.println("+---------+-----------------------+---------+");
        System.out.println("BIEN LAI GIAO DICH LOAN");
        System.out.println("NGAY G/D:          " + LocalDateTime.now().format(formatter));
        System.out.println("ATM ID:            DIGITAL-BANK-ATM 2024");
        System.out.println("SO TK:             " + getAccountNumber());
        System.out.println("SO TIEN:           " + df.format(amount));
        System.out.println("SO DU:            " + df.format(getBalance()));
        System.out.println("PHI + VAT:         " + df.format(isPremiumAccount() ? amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : amount * LOAN_ACCOUNT_WITHDRAW_FEE));
        System.out.println("+---------+-----------------------+---------+");
    }


    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            double fee = isPremiumAccount() ? amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : amount * LOAN_ACCOUNT_WITHDRAW_FEE;
            double totalAmount = amount + fee;
            if (getBalance() - totalAmount >= -LOAN_ACCOUNT_MAX_BALANCE ) {
                setBalance(getBalance() - totalAmount);
                Transaction transaction = new Transaction(getAccountNumber(), amount, true, LocalDateTime.now().format(formatter));
                addTransaction(transaction);
                log(amount);
                return true;
            } else {
                System.out.println("Khong dc rut > 100tr(tinh ca phi PHI+VAT)");
            }
        } else {
            System.out.println("Khong du dieu kien rut tien ");
        }
        return false;
    }


    @Override
    public boolean isAccepted(double amount) {
        return amount <= LOAN_ACCOUNT_MAX_BALANCE && amount%10000==0;

    }


    public double getFee(double amount) {
     return  isPremiumAccount() ? amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : amount * LOAN_ACCOUNT_WITHDRAW_FEE;
    }
}
/////////////