package vn.funix.FX38455.java.asm03.models;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Bank;
import vn.funix.FX38455.java.asm02.models.Customer;

import java.util.ArrayList;
import java.util.Scanner;

public class DigitalBank extends Bank {


    // Tìm khách hàng theo CCCD
    public Customer getCustomerById(String customerId) {
        for (Customer customer : getCustomers()) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    // Thêm khách hàng mới vào ngân hàng
    public void addCustomer(String customerId, String name) {
        Customer existingCustomer = getCustomerById(customerId);
        if (existingCustomer == null) {
            Customer newCustomer = new DigitalCustomer();
            getCustomers().add(newCustomer);
            System.out.println("Khach hang moi da duoc them.");
        } else {
            System.out.println("Khach hang da ton tai.");
        }
    }


    // Cho phép khách hàng rút tiền từ tài khoản
    public void withdraw(String customerId, String accountNumber, double amount) {
        Customer customer = getCustomerById(customerId);
        if (customer != null && customer instanceof DigitalCustomer) {
            boolean success = ((DigitalCustomer) customer).withdraw(accountNumber, amount);
            if (success) {
                System.out.println("Giao dich thanh cong.");
            } else {
                System.out.println("Giao dich khong thanh cong.");
            }
        } else {
            System.out.println("Khach hang khong ton tai.");
        }
    }


    @Override
    public void addAccount(String customerId, Account account) {
        Customer customer = getCustomerById(customerId);
//        System.out.println("tk: "+customer);
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

    ////

    public boolean validateAccount(String accountNumber) {
        // Kiểm tra định dạng số tài khoản
        if (accountNumber.length() != 6 || !accountNumber.matches("[0-9]+")) {
            return false;
        }
        return true;
    }

        public boolean validateCustomerId(String accountNumber) {
            if (accountNumber.matches("[0-9]+") && accountNumber.length() == 12) {
                return true;
        }
            return false;
        }

    ///
}