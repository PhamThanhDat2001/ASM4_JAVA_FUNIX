package vn.funix.FX38455.java.asm03.models;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Bank;
import vn.funix.FX38455.java.asm02.models.Customer;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SavingsAccount extends Account implements ReportService,Withdraw{


    public static final int SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
//    LocalDateTime time = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    @Override
    public void log(double amount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.println("+---------+-----------------------+---------+");
        System.out.println("BIEN LAI GIAO DICH SAVINGS");
        System.out.println("NGAY G/D:          " +  LocalDateTime.now().format(formatter));

        System.out.println("ATM ID:            DIGITAL-BANK-ATM 2024");

        System.out.println("SO TK:             " + getAccountNumber());

        System.out.println("SO TIEN:           " + df.format(amount));

        System.out.println("SO DU:             " + df.format(getBalance()));

        System.out.println("PHI + VAT:         0đ" );
        System.out.println("+---------+-----------------------+---------+");
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            if (getBalance() >= amount && getBalance() - amount >= 50_000) {
                setBalance(getBalance() - amount);
                Transaction transaction = new Transaction(getAccountNumber(), amount, true, LocalDateTime.now().format(formatter));
                addTransaction(transaction);
                log(amount);
                return true;
            } else {
                System.out.println("So tien trong tai khoan khong du ");
            }
        } else {
            System.out.println("Khong du dieu kien rut tien ");
        }
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (isPremiumAccount()){
            return amount >50_000 && amount%10000==0 ;
        }else {
            // return true neu dung
            return amount > 50_000 && amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW && amount%10000==0;
        }

    }
    Bank bank = new Bank();
    public void addAccount(String customerId,Account account){
        for (Customer customer : bank.getCustomers()){
            if (customer.getCustomerId().equals(customerId)){
                customer.addAccount(account);
                return;
            }
        }

    }

    public double getFee(double amount) {
        return 0*amount;
    }
}
