package vn.funix.FX38455.java.asm02.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
   private final String id;
   private final List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
    }
    public void addCustomer(Customer newCustomer){
        if (!isCustomerExisted(newCustomer.getCustomerId())) {
            customers.add(newCustomer); // Thêm khách hàng mới vào danh sách của ngân hàng
            System.out.println("Đã thêm khách hàng mới vào ngân hàng.");
        } else {
            System.out.println("Khách hàng đã tồn tại trong hệ thống.");
        }
    }
    public boolean isCustomerExisted(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return true; // Nếu số CCCD đã tồn tại trong danh sách, trả về true
            }
        }
        return false; // Nếu số CCCD không tồn tại trong danh sách, trả về false
    }

    public List<Customer> getCustomers() {
        return customers;
    }
    public Customer getCustomerById(String cccd) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(cccd)) {
                return customer;
            }
        }
        return null; // Hoặc ném ngoại lệ nếu không tìm thấy
    }
    public Customer getCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null; // Hoặc ném ngoại lệ nếu không tìm thấy
    }
//    public void addAccount(String customerId, Account account) {
//        Customer customer = getCustomerById(customerId);
//        if (customer != null) {
//            customer.addAccount(account); // Sử dụng phương thức addAccount của Customer
//        } else {
//            System.out.println("Khách hàng không tồn tại.");
//        }
//    }

//    public void addAccount(String customerId, Account account) {
//        Customer customer = getCustomerById(customerId);
//        if (customer != null) {
//            // Kiểm tra xem tài khoản có tồn tại không trước khi thêm
//            boolean isNewAccountExist = false;
//            for (Account acc : customer.getAccounts()) {
//                if (acc.getAccountNumber().equals(account.getAccountNumber())) {
//                    isNewAccountExist = true;
//                    System.out.println("Tài khoản đã tồn tại");
//                    break;
//                }
//            }
//            if (!isNewAccountExist) {
//                customer.addAccount(account); // Sử dụng phương thức addAccount của Customer
//            }
//        } else {
//            System.out.println("Khách hàng không tồn tại.");
//        }
//    }

    public void addAccount(String customerId, Account account) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            // Kiểm tra xem tài khoản có tồn tại không trước khi thêm
            boolean isNewAccountExist = false;
            for (Account acc : customer.getAccounts()) {
                if (acc.getAccountNumber().equals(account.getAccountNumber())) {
                    isNewAccountExist = true;
                    System.out.println("Tài khoản đã tồn tại");
                    break;
                }
            }

            // Kiểm tra xem mã số tài khoản đã tồn tại trong tất cả các khách hàng hay chưa
            boolean isAccountExistInAllCustomers = false;
            for (Customer otherCustomer : getCustomers()) {
                for (Account acc : otherCustomer.getAccounts()) {
                    if (acc.getAccountNumber().equals(account.getAccountNumber())) {
                        isAccountExistInAllCustomers = true;
                        break;
                    }
                }
            }

            // Nếu mã số tài khoản chưa tồn tại trong danh sách tài khoản của khách hàng
            // và chưa tồn tại trong tất cả các khách hàng, thêm tài khoản mới
            if (!isNewAccountExist && !isAccountExistInAllCustomers) {
                customer.addAccount(account);
                System.out.println("Thêm tài khoản thành công");
            } else {
                System.out.println("Mã số tài khoản đã tồn tại hoặc trùng với tài khoản của khách hàng khác. Không thể thêm tài khoản mới.");
            }
        } else {
            System.out.println("Khách hàng không tồn tại.");
        }
    }

///////////////
}