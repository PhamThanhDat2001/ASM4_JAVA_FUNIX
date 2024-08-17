package vn.funix.FX38455.java.asm02;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Bank;
import vn.funix.FX38455.java.asm02.models.Customer;
import vn.funix.FX38455.java.asm02.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final Bank bank = new Bank();

    static Scanner scanner = new Scanner(System.in);
    // kiem tra xem input co phai la so khong
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

    //menu
    public static void menu(){
        System.out.println("+-------------------------------------+");
        System.out.println("| NGAN HANG SO | FX123456@v2.0.0      |");
        System.out.println("+-------------------------------------+");

        // In menu
        System.out.println("| 1. Them khach hang                  |");
        System.out.println("| 2. Them tai khoan cho khac hang     |");
        System.out.println("| 3. Hien thi danh sach khac hang     |");
        System.out.println("| 4. Tim theo CCCD                    |");
        System.out.println("| 5. Tim theo ten khach hang          |");
        System.out.println("| 0. Thoat                            |");

        System.out.println("+-------------------------------------+");
    }

    // chọn chức năng
    public static void Choice(){
        int choice;
        User user = new User();
        do {
//            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                    addCustomer();
                    break;
                case 2:
                    addAccountNumber();
                    break;
                case 3:
                    displayCustomerInfo();
                    break;
                case 4:
                    getElementById();
                    break;
                case 5:
                    getElementByName();
                    break;
                case 0:
                    System.out.println("0");
                    break;
            }
        }while (choice!=0 && choice!=1 && choice!=2 && choice!=3 && choice!=4 && choice!=5);
    }

    // chức năng 2 thêm tài khoản
    public static void addAccountNumber() {
        System.out.println("Nhập CCCD khách hàng:");
        String cccd = scanner.next();
        scanner.nextLine(); // Đọc bỏ dòng mới

        while (!bank.isCustomerExisted(cccd)) {
            System.out.println("CCCD không tồn tại, yêu cầu nhập lại:");
            cccd = scanner.next();
            scanner.nextLine(); // Đọc bỏ dòng mới
        }

//        Customer customer = bank.getCustomerById(cccd); // Lấy đối tượng Customer theo CCCD

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

            Account account = new Account();
            account.setBalance(sodu);
            account.setAccountNumber(stk);
//            customer.addAccount(account);
            bank.addAccount(cccd, account);
            System.out.print("Bạn muốn thêm tài khoản khác cho khách hàng này? (Y/N): ");
            choice = scanner.next().charAt(0);
        } while (choice == 'Y' || choice == 'y');

        System.out.println("Mời bạn chọn tiếp chức năng: ");
        menu();
        Choice();
    }

    //chức năng 1 thêm khách hàng
    public static void addCustomer() {//////
        Scanner scanner = new Scanner(System.in);
//        Bank bank = new Bank();
        char choice;
        do {
            Customer  customer = new Customer(); // Tạo một đối tượng Customer mới
            System.out.print("Nhập tên khách hàng: ");
            String name = scanner.nextLine();
            customer.setName(name);

            System.out.print("Nhập số CCCD: ");
            String cccd = scanner.nextLine();
            customer.setCustomerId(cccd);

            bank.addCustomer(customer); // Thêm khách hàng mới vào danh sách của ngân hàng

            System.out.print("Bạn muốn tiếp tục thêm khách hàng khác? (Y/N): ");
            choice = scanner.nextLine().charAt(0);
        } while (choice == 'Y' || choice == 'y');

        System.out.println("Danh sách khách hàng sau khi thêm:");
        for (Customer cust : bank.getCustomers()) {
            System.out.println(cust.getName() + " - " + cust.getCustomerId());
        }
//        System.out.println(bank.getCustomers().get(0));

        System.out.println();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        menu();
        Choice();
    }

    //chức năng 3 hiển thị ds khách hàng
    public static void displayCustomerInfo() {

        for (Customer customer : bank.getCustomers()) {
            customer.displayInformation();
        }
        System.out.println();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        menu();
        Choice();
    }

    // chức năng 4 tìm kiếm theo id
    public static void getElementById(){
        System.out.print("Nhap CCCD can tim kiem: ");
        String cccd = scanner.next();
        Customer customer1 = bank.getCustomerById(cccd);
        while (!cccd.matches("[0-9]+") || cccd.length() !=12 || customer1 == null){
            System.out.print("CCCD khong ton tai hay nhap lai: ");
            cccd = scanner.next();
            customer1 = bank.getCustomerById(cccd);
        }
        customer1.displayInformation();
        System.out.println();
        System.out.println("Mời bạn chọn tiếp chức năng: ");
        menu();
        Choice();
    }

    // chức năng 5 tìm kiếm theo tên
    public static void getElementByName(){
        System.out.print("Nhập từ khoá tìm kiếm theo tên khách hàng: ");
        scanner.nextLine(); // Đọc và bỏ dòng mới
        String keyword = scanner.nextLine();

        List<Customer> foundCustomers = new ArrayList<>();
        // Tìm khách hàng theo từ khoá và thêm vào danh sách tim kiếm
        for (Customer customer : bank.getCustomers()) {
            //contains kiểm tra xem trong chuỗi có chuỗi con không
            if (customer.getName().toLowerCase().contains(keyword.toLowerCase())) {
                foundCustomers.add(customer);
            }
        }
        if (foundCustomers.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào phù hợp với từ khoá tìm kiếm.");
        } else {
            System.out.println("Danh sách khách hàng phù hợp với từ khoá tìm kiếm:");
            for (Customer customer : foundCustomers) {
                customer.displayInformation();
                System.out.println();
            }
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
        menu();
        Choice();
    }
    public static void main(String[] args) {
        int choice;
        User user = new User();
        do {
            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice){
                case 1:
                    addCustomer();
                    break;
                case 2:
                    addAccountNumber();
                    break;
                case 3:
                    displayCustomerInfo();
                    break;
                case 4:
                    getElementById();
                    break;
                case 5:
                    getElementByName();
                    break;
                case 0:
                    System.out.println("0");
                    break;
            }
        }while (choice!=0 && choice!=1 && choice!=2 && choice!=3 && choice!=4 && choice!=5);
    }
}
