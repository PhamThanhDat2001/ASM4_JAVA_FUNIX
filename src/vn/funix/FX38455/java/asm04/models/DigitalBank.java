package vn.funix.FX38455.java.asm04.models;

import vn.funix.FX38455.java.asm04.dao.AccountDao;
import vn.funix.FX38455.java.asm04.dao.TransactionDao;
import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.models.Customer;
import vn.funix.FX38455.java.asm04.dao.CustomerDao;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DigitalBank  extends Bank{

    public DigitalBank() {
    }

    // Phương thức lấy ra một khách hàng có ID cho trước
    public void getCustomerById(List<Customer> customerList, String customerId) {
       for (Customer customer : customerList){
           if (customer.getCustomerId().equals(customerId)){
               DecimalFormat df = new DecimalFormat("#,### đ");
               System.out.print(customerId + " |       ");
               System.out.print(customer.getName() + " |    ");
               if (customer.isPremiumAccount()) {
                   System.out.print("Premium |   ");
               } else {
                   System.out.print("Normal |   ");
               }
               System.out.println("Tổng số tiền: " + df.format(customer.getBalance()));

               System.out.println();

               List<Account> customerAccounts = customer.getAccounts(customerId);

               if (customerAccounts.isEmpty()) {
                   System.out.println("Khách hàng không có tài khoản.");
               } else {
                   for (int i = 0; i < customerAccounts.size(); i++) {
                       Account account = customerAccounts.get(i);
                       System.out.println((i + 1) + "      " + account.getAccountNumber() + " |  SAVINGS              " + df.format(account.getBalance()));
                   }
               }
           }
       }

    }

//    phương thức để hiển thị thông tin số tài khoản
    public void displayAccountInfoByAccountNumber(List<Customer> customerList, String accountNumber) {
        boolean found = false;

        for (Customer customer : customerList) {
            List<Account> accounts = customer.getAccounts(customer.getCustomerId());

            for (Account account : accounts) {
                if (account.getAccountNumber().equals(accountNumber)) {
//                    System.out.println("Số tài khoản: " + account.getAccountNumber());
//                    System.out.println("Tên người dùng: " + customer.getName());
                    System.out.println("Gửi tiền đến tài khoản "+  account.getAccountNumber() +" | " + customer.getName() );
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy thông tin cho số tài khoản " + accountNumber);
        }
    }

    public Customer getCustomer(List<Customer> customerList, String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                DecimalFormat df = new DecimalFormat("#,### đ");
                System.out.print(customerId + " |       ");
                System.out.print(customer.getName() + " |    ");
                if (customer.isPremiumAccount()) {
                    System.out.print("Premium |   ");
                } else {
                    System.out.print("Normal |   ");
                }
//                double totalBalance = customer.getTotalBalance();
                System.out.println("Tổng số tiền: " + df.format(customer.getBalance()));

                System.out.println();

                List<Account> customerAccounts = customer.getAccounts(customerId);

                if (customerAccounts.isEmpty()) {
                    System.out.println("Khách hàng không có tài khoản.");
                } else {
                    for (int i = 0; i < customerAccounts.size(); i++) {
                        Account account = customerAccounts.get(i);
                        System.out.println((i + 1) + "      " + account.getAccountNumber() + " |  SAVINGS              " + df.format(account.getBalance()));
                    }
                }
                return customer;
            }
        }
        return null;
    }
//    private double getTotalBalance() {
//        List<Account> customerAccounts = getAccounts(getCustomerId());
//        double totalBalance = 0.0;
//        for (Account account : customerAccounts) {
//            totalBalance += account.getBalance();
//        }
//        return totalBalance;
//    }
    // Phương thức chuyển tiền giữa 2 tài khoản
    public void transfers(Scanner scanner, String customerId) {
        // Get the customer object based on the provided customer ID
        Customer customer = getCustomer(CustomerDao.list(), customerId);

        String sendingAccountNumber;
        Account sendingAccount;
        do {
            System.out.print("Nhập số tài khoản gửi hoặc nhập 'no' để thoát: ");
            sendingAccountNumber = scanner.next();
            if (sendingAccountNumber.equalsIgnoreCase("no")) {
                return; // Thoát khỏi phương thức nếu người dùng nhập "no"
            }
            // Check if the sending account number is valid and belongs to the customer
            sendingAccount = Customer.getAccountByAccountNumber(customer.getAccounts(), sendingAccountNumber);
            if (sendingAccount == null) {
                System.out.println("Số tài khoản gửi không hợp lệ hoặc không thuộc quyền sở hữu của khách hàng.");
            } else if (!sendingAccountNumber.matches("\\d{6}")) {
                System.out.println("Số tài khoản phải là 6 chữ số.");
                sendingAccount = null;
            }
        } while (sendingAccount == null);

        // Ask for the receiving account number
        String receivingAccountNumber;
        Account receivingAccount;
        do {
            System.out.print("Nhập số tài khoản nhận: ");
            receivingAccountNumber = scanner.next();

            // Check if the receiving account number is valid
            receivingAccount = Customer.getAccountByAccountNumber(AccountDao.list(), receivingAccountNumber);
            if (receivingAccount == null) {
                System.out.println("Số tài khoản nhận không hợp lệ.");
            }
        } while (receivingAccount == null);

        // Display information about the receiving account
        List<Customer> customers = CustomerDao.list();
        displayAccountInfoByAccountNumber(customers, receivingAccountNumber);

        // Ask for the amount to transfer and validate it
        double amount;
        do {
            System.out.print("Nhập số tiền chuyển: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Vui lòng nhập một số.");
                System.out.print("Nhập số tiền chuyển: ");
                scanner.next();
            }
            amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Số tiền phải lớn hơn 0.");
            }
        } while (amount <= 0);

        // Confirm the transfer
        System.out.println("Xác nhận thực hiện chuyển " + amount + "đ từ tài khoản [" + sendingAccountNumber + "] đến tài khoản [" + receivingAccountNumber + "] (Y/N): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("y")) {
            // Perform the transfer
            sendingAccount.transfers(receivingAccount, amount);
        } else {
            System.out.println("Chuyển tiền đã bị huỷ bỏ.");
        }
    }






    // Phương thức withdraw trong DigitalBank
    public void withdraw(Scanner scanner, String customerId) {
        // Lấy danh sách khách hàng từ DAO
        List<Customer> customers = CustomerDao.list();

        while (true) {
            // Kiểm tra customerId hợp lệ
            if (!customerId.matches("\\d{12}")) {
                System.out.println("Mã số khách hàng không hợp lệ.");
            } else {
                // Kiểm tra customerId có tồn tại trong danh sách khách hàng không
                Customer customer = getCustomer(customers, customerId);
                if (customer == null) {
                    System.out.println("Mã số khách hàng không tồn tại.");
                } else {
                    // Gọi phương thức withdraw của đối tượng Customer
                    customer.withdraw(scanner);
                    break; // Thoát khỏi vòng lặp khi đã thực hiện thành công withdraw
                }
            }

            // Nhập lại customerId
            System.out.print("Nhập lại mã số khách hàng: ");
            customerId = scanner.next();
        }
    }

//    private Customer getCustomerById(String customerId) {
//        for (Customer customer : customers) {
//            if (customer.getCustomerId().equals(customerId)) {
//                return customer;
//            }
//        }
//        return null; // Trả về null nếu không tìm thấy customer với customerId đã cho
//    }
}
