package vn.funix.FX38455.java.asm04.models;

import vn.funix.FX38455.java.asm04.dao.AccountDao;
import vn.funix.FX38455.java.asm04.dao.CustomerDao;
import vn.funix.FX38455.java.asm04.dao.TransactionDao;
import vn.funix.FX38455.java.asm04.service.ITransfer;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerId; // Thêm thuộc tính customerId để xác định tài khoản thuộc khách hàng nào
    private String accountNumber;
    private double balance;

    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    public Account(String accountNumber, double balance, String customerId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Getter và setter cho customerId
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public List<Transaction> getTransactions(String accountNumber) {
        List<Transaction> allTransactions = TransactionDao.list();

        // Sử dụng stream và lambda để lọc các giao dịch có số tài khoản trùng với accountNumber
        List<Transaction> accountTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());

        return accountTransactions;
    }


    public  void log(double amount, TransactionType transactionType, Account receiveAccount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.println("+---------+-----------------------+---------+");
        System.out.println("| BIEN LAI GIAO DICH SAVINGS"+" |");
        System.out.println("| NGAY G/D:          " + LocalDateTime.now().format(formatter) +" |");
        System.out.println("| ATM ID:            DIGITAL-BANK-ATM 2024"+" |");
        System.out.println("| SO TK:             " + getAccountNumber()+" |");
        if (transactionType == TransactionType.TRANSFER) {
            System.out.println("| TAI KHOAN NHAN:    " + receiveAccount.getAccountNumber()+" |");
        }
        System.out.println("| SO TIEN RUT:       " + df.format(amount)+" |");
        System.out.println("| SO DU:             " + df.format(getBalance())+" |");
        System.out.println("| PHI + VAT:         0đ" +" |");
        System.out.println("| LOAI GIAO DICH:    " + transactionType+" |");

        System.out.println("+---------+-----------------------+---------+");
    }

//     Thêm phương thức displayTransactionsList() để hiển thị danh sách giao dịch

    public void displayTransactionsList() {
        List<Transaction> transactions = getTransactions(this.accountNumber);
        boolean hasTransactions = false;

        for (Transaction transaction : transactions) {
            transaction.displayTranss();
            hasTransactions = true;
        }

        if (!hasTransactions) {
            System.out.println("Không có giao dịch nào.");
        }
    }



    public void createTransaction(double amount, String time, boolean status, TransactionType type) {
        // Tạo một giao dịch mới
        Transaction transaction = new Transaction(getAccountNumber(), amount, status, time, type.toString());

        // Cập nhật số dư tài khoản
        if (type == TransactionType.DEPOSIT) {
            setBalance(getBalance() + amount);
        } else if (type == TransactionType.WITHDRAW || type == TransactionType.TRANSFER) {
            setBalance(getBalance() - amount);
        }

        // Lấy ra danh sách giao dịch của số tài khoản từ tệp
        List<Transaction> accountTransactions = TransactionDao.list();

        // Thêm giao dịch mới vào danh sách
        accountTransactions.add(transaction);

        // Lưu lại danh sách giao dịch đã cập nhật vào tệp
        try {
            TransactionDao.save(accountTransactions);
            // In thông báo về giao dịch
            System.out.println("Giao dịch " + type + " thành công.");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi lưu giao dịch: " + e.getMessage());
        }

    }


    public String getAccountNumber() {
        return accountNumber;
    }
    public boolean isPremiumAccount() {
        return this.balance >= 10_000_000;
    }


    public void transfers(Account receiveAccount, double amount) {
    if (amount > 0 && this.balance >= amount) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            // Tạo giao dịch rút tiền từ tài khoản gửi
            this.createTransaction(amount, currentTime, true, TransactionType.TRANSFER);

            // Tạo giao dịch tiền nhận vào tài khoản nhận
            receiveAccount.createTransaction(amount, currentTime, true, TransactionType.DEPOSIT);

            // Cập nhật thông tin tài khoản gửi và nhận
            AccountDao.update(this);
            AccountDao.update(receiveAccount);

            // Cập nhật thông tin tài khoản và khách hàng sau khi chuyển tiền
            updateCustomerAndAccountTranfers(receiveAccount);

            // Ghi log giao dịch
            log(amount, TransactionType.TRANSFER, receiveAccount);
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi cập nhật tài khoản hoặc lưu giao dịch: " + e.getMessage());
        }
    } else {
        System.out.println("Chuyển tiền không thành công. Số dư không đủ hoặc số tiền chuyển không hợp lệ.");
    }
}


    public boolean withdraw(double amount) {
        // Kiểm tra điều kiện rút tiền
        if (isAccepted(amount)) {
            if (getBalance() >= amount && getBalance() - amount >= 50_000) {
                // Rút tiền thành công
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN")));

                try {

                    // Lưu cập nhật số dư tài khoản
                    AccountDao.update(this);

                    // Tạo giao dịch và lưu vào transaction.dat
                    createTransaction(amount, time, true, TransactionType.WITHDRAW);
//                    TransactionDao.save(getTransactions(this.getAccountNumber())); // truyền số tài khoản hiện tại vào để lấy danh sách giao dịch

                    System.out.println("Rút tiền thành công.");

                    // Cập nhật thông tin tài khoản và khách hàng sau khi rút tiền thành công
                    updateCustomerAndAccount();
                    log(amount,TransactionType.WITHDRAW, this);
                    return true; // Kết thúc phương thức khi rút tiền thành công
                } catch (IOException e) {
                    System.out.println("Đã xảy ra lỗi khi cập nhật tài khoản hoặc lưu giao dịch: " + e.getMessage());
                }
            } else {
                // Thông báo khi rút tiền vượt quá số dư hoặc hạn mức tối đa
                System.out.println("Số tiền trong tài khoản không đủ hoặc rút tiền vượt quá hạn mức.");
            }
        } else {
            // Thông báo khi không đủ điều kiện rút tiền
            System.out.println("Không đủ điều kiện rút tiền.");
        }
        return false; // Trả về false khi rút tiền thất bại
    }


    public static final int SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;
    public boolean isAccepted(double amount) {
        if (isPremiumAccount()){
            return amount >=50_000 && amount%10000==0 ;
        }else {
            // return true neu dung
            return amount >= 50_000 && amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW && amount%10000==0;
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }


    // method của WITHDRAW
    private void updateCustomerAndAccount() {
        // Lấy danh sách tất cả các tài khoản của khách hàng từ AccountDao
        List<Account> allAccounts = AccountDao.list();

        // Lấy danh sách tất cả các khách hàng từ CustomerDao
        List<Customer> allCustomers = CustomerDao.list();

        // Cập nhật thông tin số dư của tài khoản trong danh sách tất cả các tài khoản
        for (Account account : allAccounts) {
            if (account.getAccountNumber().equals(this.getAccountNumber())) {
                account.setBalance(this.getBalance()); // Cập nhật số dư mới
                break;
            }
        }

        // Cập nhật thông tin số dư của tài khoản trong danh sách tất cả các khách hàng
        for (Customer customer : allCustomers) {
            List<Account> customerAccounts = customer.getAccounts(); // Lấy danh sách tài khoản của khách hàng
            for (Account account : customerAccounts) {
                if (account.getAccountNumber().equals(this.getAccountNumber())) {
                    account.setBalance(this.getBalance()); // Cập nhật số dư mới
                    break;
                }
            }
        }

        // Lưu lại danh sách tất cả các tài khoản và khách hàng đã được cập nhật
        try {
            AccountDao.save(allAccounts);
            CustomerDao.save(allCustomers);
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi cập nhật thông tin tài khoản hoặc khách hàng: " + e.getMessage());
        }
    }

    // method của Tranfers
    private void updateCustomerAndAccountTranfers(Account receiveAccount) {
        // Lấy danh sách tất cả các tài khoản của khách hàng từ AccountDao
        List<Account> allAccounts = AccountDao.list();

        // Lấy danh sách tất cả các khách hàng từ CustomerDao
        List<Customer> allCustomers = CustomerDao.list();

        // Cập nhật thông tin số dư của tài khoản trong danh sách tất cả các tài khoản
        for (Account account : allAccounts) {
            if (account.getAccountNumber().equals(this.getAccountNumber())) {
                account.setBalance(this.getBalance()); // Cập nhật số dư mới của tài khoản gửi
            }
            if (account.getAccountNumber().equals(receiveAccount.getAccountNumber())) {
                account.setBalance(receiveAccount.getBalance()); // Cập nhật số dư mới của tài khoản nhận
            }
        }

        // Cập nhật thông tin số dư của tài khoản trong danh sách tất cả các khách hàng
        for (Customer customer : allCustomers) {
            List<Account> customerAccounts = customer.getAccounts(); // Lấy danh sách tài khoản của khách hàng
            for (Account account : customerAccounts) {
                if (account.getAccountNumber().equals(this.getAccountNumber())) {
                    account.setBalance(this.getBalance()); // Cập nhật số dư mới của tài khoản gửi
                }
                if (account.getAccountNumber().equals(receiveAccount.getAccountNumber())) {
                    account.setBalance(receiveAccount.getBalance()); // Cập nhật số dư mới của tài khoản nhận
                }
            }
        }

        // Lưu lại danh sách tất cả các tài khoản và khách hàng đã được cập nhật
        try {
            AccountDao.save(allAccounts); // Lưu lại danh sách tất cả các tài khoản vào account.dat
            CustomerDao.save(allCustomers); // Lưu lại danh sách tất cả các khách hàng vào customer.dat
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi cập nhật thông tin tài khoản hoặc khách hàng: " + e.getMessage());
        }
    }
}
