package vn.funix.FX38455.java.asm03;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Bank;
import vn.funix.FX38455.java.asm02.models.Customer;
import vn.funix.FX38455.java.asm02.models.User;
import vn.funix.FX38455.java.asm03.models.*;

import java.util.Scanner;

public class Main {
    public static final int  EXIT_COMMAND_CODE = 0;
    public static final int  EXIT_ERROR_CODE = -1;
    public static final Scanner scanner = new Scanner(System.in);
    public static final DigitalBank activeBank = new DigitalBank();
    public static final String CUSTOMER_ID = "001215000001";
    public static final String CUSTOMER_NAME = "FUNIX";


    //menu
    public static void menu(){
        System.out.println("+-----------------------------------------+");
        System.out.println("| NGAN HANG SO | FX38455@v3.0.0           |");
        System.out.println("+-----------------------------------------+");

        // In menu
        System.out.println("| 1. Thong tin khac hang                  |");
        System.out.println("| 2. Them tai khoan ATM                   |");
        System.out.println("| 3. Them tai khoan tin dung              |");
        System.out.println("| 4. Rut tien                             |");
        System.out.println("| 5. Lich su giao dich                    |");
        System.out.println("| 0. Thoat                                |");

        System.out.println("+-----------------------------------------+");
    }

    // chọn chức năng
    public static void Choice(){
        int choice;
        do {
            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                    showCustomer();
                    break;
                case 2:
                    addAccountATM();
                    break;
                case 3:
                    addAccountCredit();
                    break;
                case 4:
                    withdraw_Money();
                    break;
                case 5:
                    displayTransaction();
                    break;
                case 0:
                    System.out.println("0");
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

    // 1 Thong tin khach hang
    public static void showCustomer(){

        Customer existingCustomer = activeBank.getCustomerById(CUSTOMER_ID);
        if (existingCustomer == null) {
            System.out.println("Khách hàng không tồn tại.");
            return;
        }

        // Hiển thị thông tin của khách hàng đã được truyền vào
        existingCustomer.displayInformation();

        System.out.println();
        System.out.println("+---------------------+");

        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    // 2 them tai khoan atm
    public static void addAccountATM() {
        char choice;
        do {
            System.out.println("Nhập mã STK gồm 6 chữ số:");
            String stk = scanner.next();
            while (stk.length() != 6 || !stk.matches("[0-9]+")) {
                System.out.println("Nhập lại STK gồm 6 chữ số:");
                stk = scanner.next();
            }
            double sodu = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Nhập số dư:");
                if (scanner.hasNextDouble()) {
                    sodu = scanner.nextDouble();
                    if (sodu >= 50_000) {
                        validInput = true;
                    } else {
                        System.out.println("Số dư không được nhỏ hơn 50,000:");
                    }
                } else {
                    System.out.println("Số dư phải là một số hợp lệ:");
                    scanner.next(); // Đọc bỏ giá trị không hợp lệ
                }
            }

            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setBalance(sodu);
            savingsAccount.setAccountNumber(stk);
//            System.out.println("abc" +loanAccount);
            activeBank.addAccount(CUSTOMER_ID, savingsAccount);

            System.out.print("Bạn muốn thêm tài khoản khác cho khách hàng này? (Y/N): ");
            choice = scanner.next().charAt(0);
        } while (choice == 'Y' || choice == 'y');

        System.out.println();
        System.out.println("+---------------------+");

        System.out.println("Mời bạn chọn tiếp chức năng: ");
//        menu();
        Choice();
    }

    // 3 them tai khoan tin dung
    public static void addAccountCredit() {
        char choice;
        do {
            System.out.println("Nhập mã STK gồm 6 chữ số:");
            String stk = scanner.next();
            while (stk.length() != 6 || !stk.matches("[0-9]+")) {
                System.out.println("Nhập lại STK gồm 6 chữ số:");
                stk = scanner.next();
            }
            LoanAccount loanAccount = new LoanAccount();
//            loanAccount.setBalance(sodu);
            loanAccount.setAccountNumber(stk);
//            System.out.println("abc" +loanAccount);
            activeBank.addAccount(CUSTOMER_ID, loanAccount);

            System.out.print("Bạn muốn thêm tài khoản khác cho khách hàng này? (Y/N): ");
            choice = scanner.next().charAt(0);
        } while (choice == 'Y' || choice == 'y');

        System.out.println();
        System.out.println("+---------------------+");

        System.out.println("Mời bạn chọn tiếp chức năng: ");
//        menu();
        Choice();
    }

    // 4 rut tien
    public static void withdraw_Money() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);

//        if (customer == null) {
//            System.out.println("Customer kh.");
//            return;
//        }

        String stk;
        boolean accountExists = false;

        do {
            System.out.print("Nhập STK muốn rút: ");
            stk = scanner.next();
            for (Account account : customer.getAccounts()) {
                if (stk.equals(account.getAccountNumber())) {
                    accountExists = true;
                    break;
                }
            }
            if (!accountExists) {
                System.out.println("STK không tồn tại, yêu cầu nhập lại:");
            }
        } while (!accountExists);

        System.out.print("Nhập số tiền cần rút: ");
        double money = scanner.nextDouble(); // getValidChoose() có thể cần sửa lại để trả về một số thực
//        System.out.println("Số tiền cần rút: " + money);

        for (Account account : customer.getAccounts()) {
            if (stk.equals(account.getAccountNumber())) {
                if (account instanceof SavingsAccount) {
                    ((SavingsAccount) account).withdraw(money);
                } else if (account instanceof LoanAccount) {
                    ((LoanAccount) account).withdraw(money);
                }
            }
        }

        System.out.println();
        System.out.println("+---------------------+");
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    // 5 hien thi giao dich
    public static void displayTransaction(){
//        Customer existingCustomer = activeBank.getCustomerById(CUSTOMER_ID);
//        System.out.println("+-------------------------------------------------------+");
////        showCustomer();
//        existingCustomer.displayInformation();
//        System.out.println();

        System.out.println("+-------------------------------------------------------+");
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if (customer == null) {
            System.out.println("Khách hàng không tồn tại.");
            return;
        }

        System.out.println("Thông tin khách hàng:");
        customer.displayInformation();
        System.out.println();
        System.out.print("     Account |    ");
        System.out.print("Amount |                ");
        System.out.print("Time |            ");
        System.out.print("Transaction ID              ");
        System.out.println();
//        customer.getAccounts(): Lấy danh sách các tài khoản của khách hàng
//        account.getTransactions(): Lấy danh sách các giao dịch của một tài khoản
        for (Account account : customer.getAccounts()) {
//            System.out.println(account.getAccountNumber() + ":");
            for (Transaction transaction : account.getTransactions()) {
                transaction.displayTranss();
                System.out.println();
            }
        }
//        System.out.println("+-------------------------------------------------------+");
        System.out.println();
        System.out.println("+---------------------+");

        System.out.println("Mời bạn chọn tiếp chức năng: ");
        Choice();
    }

    public static void main(String[] args) {
        int choice;
        DigitalCustomer customer = new DigitalCustomer();
        customer.setCustomerId(CUSTOMER_ID);
        customer.setName(CUSTOMER_NAME);
        activeBank.addCustomer(customer);
        do {
            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                    showCustomer();
                    break;
                case 2:
                    addAccountATM();
                    break;
                case 3:
                    addAccountCredit();
                    break;
                case 4:
                    withdraw_Money();
                    break;
                case 5:
                    displayTransaction();
                    break;
                case 0:
                    System.out.println("0");
                    break;
            }
        }while (choice!=0 && choice!=1 && choice!=2 && choice!=3 && choice!=4 && choice!=5);
    }

}
