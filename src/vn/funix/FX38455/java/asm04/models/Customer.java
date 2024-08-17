package vn.funix.FX38455.java.asm04.models;

import vn.funix.FX38455.java.asm04.dao.AccountDao;
import vn.funix.FX38455.java.asm04.dao.TransactionDao;
import vn.funix.FX38455.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.FX38455.java.asm04.exception.CustomerIdValidator;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerId;
    private String name;
    private List<Account> accounts;

    public Customer(String customerId, String name) throws CustomerIdNotValidException {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new ArrayList<>();

        if (!isCustomerExist(customerId)) {
            if (CustomerIdValidator.validateCustomerId(customerId)) {
                System.out.println("Da them khach hang: " + customerId + " vao danh sach khac hang");
                addToCustomersFile(customerId, name);
            } else {
                throw new CustomerIdNotValidException("Khach hang: " + customerId + " da ton tai, them khong thanh cong");
            }
        } else {
            throw new CustomerIdNotValidException("Khach hang: " + customerId + " da ton tai, them khong thanh cong");
        }
    }

    private boolean isCustomerExist(String customerId) {
        try (Scanner scanner = new Scanner(new File("store/customers.dat"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 2 && parts[0].equals(customerId)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addToCustomersFile(String customerId, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("store/customers.dat", true))) {
            writer.write(customerId + ", " + name);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Customer(List<String> values) throws CustomerIdNotValidException {
        this(values.get(0), values.get(1));
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Account> getAccounts(String customerId) {
        // Lấy danh sách tất cả các tài khoản từ AccountDao
        List<Account> allAccounts = AccountDao.list();

        // Lọc ra các tài khoản của khách hàng có customerId bằng customerId hiện tại
        return allAccounts.stream()
                .filter(account -> account.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }


    public void displayInformation() {
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.print(getCustomerId() + " |       ");
        System.out.print(getName() + " |    ");
        if (isPremiumAccount()) {
            System.out.print("Premium |   ");
        } else {
            System.out.print("Normal |   ");
        }

//        double totalBalance = getTotalBalance();
        System.out.println("Tổng số tiền: " + df.format(getBalance()));
//        System.out.print(getAccounts() + " |    ");
        System.out.println();

        List<Account> customerAccounts = getAccounts(getCustomerId());

        if (customerAccounts.isEmpty()) {
            System.out.println("Khách hàng không có tài khoản.");
        } else {
            for (int i = 0; i < customerAccounts.size(); i++) {
                Account account = customerAccounts.get(i);
                System.out.println((i + 1) + "      " + account.getAccountNumber() + " |  SAVINGS              " + df.format(account.getBalance()));
            }
        }
    }

    public double getTotalBalance() {
        List<Account> customerAccounts = getAccounts(getCustomerId());
        double totalBalance = 0.0;
        for (Account account : customerAccounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }



    public static Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null; // Trả về null nếu không tìm thấy tài khoản với số tài khoản đã cho
    }


    public double getBalance() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }


    public void transfers(Scanner scanner) {

    }

    public void displayTransactionInformation() {
        // Implement your logic for displaying transaction information
    }

    public void withdraw(Scanner scanner) {
        List<Account> accounts = getAccounts(); // Lấy danh sách tài khoản từ getAccounts()

        String accountNumber;
        Account account;
        do {
            System.out.print("Nhập số tài khoản (6 chữ số): ");
            accountNumber = scanner.next();

            // Kiểm tra số tài khoản có hợp lệ là 6 số và không chứa chữ
            if (!accountNumber.matches("\\d{6}")) {
                System.out.println("Số tài khoản không hợp lệ. Vui lòng nhập lại.");
                continue;
            }

            // Kiểm tra sự tồn tại của tài khoản
            account = getAccountByAccountNumber(accounts, accountNumber);
            if (account == null) {
                System.out.println("Tài khoản không tồn tại. Vui lòng nhập lại.");
            } else {
                break; // Thoát khỏi vòng lặp nếu tài khoản tồn tại và hợp lệ
            }
        } while (true);

        double amount;
        do {
            System.out.print("Nhập số tiền rút: ");

            while (!scanner.hasNextDouble()) {
                System.out.print("Số tiền không hợp lệ. Vui lòng nhập lại.");
                scanner.next(); // Tiếp tục yêu cầu nhập
            }
            amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Số tiền phải lớn hơn 0. Vui lòng nhập lại.");
            }
        } while (amount <= 0);

        // Gọi hàm rút tiền của tài khoản
        account.withdraw(amount);
    }


    public boolean isPremiumAccount() {
        return accounts.stream().anyMatch(Account::isPremiumAccount);
    }

}
