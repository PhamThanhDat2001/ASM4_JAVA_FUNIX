package vn.funix.FX38455.java.asm04;

import vn.funix.FX38455.java.asm04.dao.AccountDao;
import vn.funix.FX38455.java.asm04.dao.CustomerDao;
import vn.funix.FX38455.java.asm04.models.*;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.*;
import java.util.*;

public class Main {

    public static final Scanner scanner = new Scanner(System.in);
    public static final Bank bank = new Bank();
    //menu
    public static void menu(){
        System.out.println("+-----------------------------------------+");
        System.out.println("| NGAN HANG SO | FX38455@v4.0.0           |");
        System.out.println("+-----------------------------------------+");

        // In menu
        System.out.println("| 1. Xem danh sách khách hàng             |");
        System.out.println("| 2. Nhập danh sách khách hàng            |");
        System.out.println("| 3. Thêm tài khoản ATM                   |");
        System.out.println("| 4. Chuyển tiền                          |");
        System.out.println("| 5. Rút tiền                             |");
        System.out.println("| 6. Tra cứu lịch sử giao dịch            |");
        System.out.println("| 0. Thoat                                |");

        System.out.println("+-----------------------------------------+");
    }
    // chọn chức năng
    public static void Choice(){

        int choice;
        do {
//            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                   displayCustomer();
                    break;
                case 2:
                    inputCustomer();
                    break;
                case 3:
                    addAccount();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    withdrawMoney();
                    break;
                case 6:
                    transactionHistory();
                    break;
            }
        }while (choice!=0 && choice!=1 && choice!=2 && choice!=3 && choice!=4 && choice!=5);
    }

    public static int getValidChoose(){
        int choice;
        while (true){
            try{
                choice = Integer.parseInt(scanner.next());
                break;
            }catch (NumberFormatException e){
                System.out.println("Yeu cau nhap so");
                System.out.print("Nhap lai: ");
            }
        }
        return choice;
    }

    // chuc nang 2 nhap danh sach khach hang
    public static void inputCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập đường dẫn đến tệp văn bản (.txt): ");
        String txtFilePath = scanner.nextLine();

        File txtFile = new File(txtFilePath);

        while (!txtFile.exists()) {
            System.out.println("Tệp không tồn tại. Hãy nhập lại.");
            txtFilePath = scanner.nextLine();
            txtFile = new File(txtFilePath);
        }

        // Sử dụng phương thức addCustomers để xử lý thêm khách hàng từ file
        bank.addCustomers(txtFilePath);

        System.out.println("-----------------------------");
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    // chuc nang 1 xem danh sach khach hang
    public static void displayCustomer() {

        bank.showCustomers();
        System.out.println();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println();
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    // chuc nang 3 them tai khoan
    public static void addAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã số khách hàng: ");
        String cccd = scanner.next();

        List<Customer> customers = CustomerDao.list();
        Customer foundCustomer = null;

        // Kiểm tra xem cccd đã tồn tại trong danh sách khách hàng và có đúng định dạng hay không
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(cccd)) {
                foundCustomer = customer;
                break;
            }
        }

        while (foundCustomer == null || cccd.length() != 12 || !cccd.matches("[0-9]+")) {
            System.out.println("Mã số khách hàng không hợp lệ hoặc không tồn tại. Nhập lại: ");
            cccd = scanner.next();
            for (Customer customer : customers) {
                if (customer.getCustomerId().equals(cccd)) {
                    foundCustomer = customer;
                    break;
                }
            }
        }
        System.out.println("Nhập số tài khoản gồm 6 chữ số: ");
        String accountNumber = scanner.next();

        while (accountNumber.length() != 6 || !accountNumber.matches("[0-9]+")) {
            System.out.println("Nhập lại STK gồm 6 chữ số:");
            accountNumber = scanner.next();
        }

//        boolean accountExists = bank.isAccountExisted(accountNumber);
        boolean accountExists = bank.isAccountExits(accountNumber);
        double balance = 0;
        if (accountExists) {
            System.out.println("Số tài khoản đã tồn tại. Không thể thêm mới.");
        } else {
            // Tiếp tục xử lý thêm tài khoản mới
            System.out.print("Nhập số dư tài khoản >= 50.000: ");
            balance = scanner.nextDouble();
            while (balance < 50_000) {
                System.out.print("Nhập số dư tài khoản >= 50.000:");
                balance = scanner.nextDouble();
            }
            Account newAccount = new Account(accountNumber, balance, foundCustomer.getCustomerId());
            foundCustomer.getAccounts().add(newAccount);
            // Ghi lại danh sách khách hàng đã được cập nhật vào tệp customers.dat
            try {
                CustomerDao.save(customers);
            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi khi ghi danh sách khách hàng: " + e.getMessage());
            }

            // Cập nhật danh sách tất cả các tài khoản trong hệ thống
            List<Account> allAccounts = AccountDao.list();
            allAccounts.add(newAccount);
            try {
                AccountDao.save(allAccounts);
            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi khi ghi danh sách tài khoản: " + e.getMessage());
            }

            System.out.println("Tài khoản mới đã được thêm thành công.");
        }

        System.out.println();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println();
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    // chuc nang 4 chuyen tien
    public static void transferMoney() {
        DigitalBank digitalBank = new DigitalBank();
        List<Customer> customers = CustomerDao.list();
        String cccd;

        while (true) {
            System.out.print("Nhập mã số khách hàng : ");
            cccd = scanner.next();
            // Kiểm tra mã số khách hàng có đúng định dạng không
            if (!cccd.matches("\\d{12}")) {
                System.out.println("Mã số khách hàng không hợp lệ. Vui lòng nhập lại.");
                continue;
            }

            // Kiểm tra mã số khách hàng có tồn tại không
            String finalCccd = cccd;
            boolean customerExists = customers.stream()
                    .anyMatch(customer -> customer.getCustomerId().equals(finalCccd));
            if (!customerExists) {
                System.out.println("Mã số khách hàng không tồn tại. Vui lòng nhập lại.");
                continue;
            }
            break;
        }

        digitalBank.transfers(scanner, cccd);
        System.out.println();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println();
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    //chuc nang 5 rut tien
    public static void withdrawMoney() {
        DigitalBank digitalBank = new DigitalBank();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã số khách hàng: ");
        String cccd = scanner.next();
        digitalBank.withdraw(scanner, cccd);
        System.out.println();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println();
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }



    public static void transactionHistory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã số khách hàng: ");
        String customerId = scanner.next();

        List<Customer> customers = CustomerDao.list();
        Customer foundCustomer = null;

        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                foundCustomer = customer;
                break;
            }
        }

        if (foundCustomer == null) {
            System.out.println("Không tìm thấy khách hàng với mã số: " + customerId);
        } else {
            // Hiển thị thông tin của khách hàng
            System.out.println("Thông tin khách hàng:");
            DigitalBank digitalBank = new DigitalBank();
           digitalBank.getCustomerById(customers,customerId);

            // Hiển thị lịch sử giao dịch cho mỗi tài khoản của khách hàng
            for (Account account : foundCustomer.getAccounts()) {
                System.out.println("Lịch sử giao dịch của tài khoản " + account.getAccountNumber() + ":");
                account.displayTransactionsList();
            }
        }

        // Hiển thị menu và chức năng tiếp theo
        System.out.println();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println();
        menu();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }


    public static void main(String[] args) {

        int choice;
        do {
            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                   displayCustomer();
                    break;
                case 2:
                    inputCustomer();
                    break;
                case 3:
                    addAccount();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    withdrawMoney();
                    break;
                case 6:
                    transactionHistory();
                    break;
            }
        }while (choice!=0 && choice!=1 && choice!=2 && choice!=3 && choice!=4 && choice!=5);
    }
}
