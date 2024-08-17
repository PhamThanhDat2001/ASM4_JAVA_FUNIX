package vn.funix.FX38455.java.asm03.models;

import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Customer;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DigitalCustomer extends Customer  {

//    public DigitalCustomer(String customerId, String name) {
//        super(customerId, name);
//        this.setAccounts(new ArrayList<>());
//    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = getAccountByNumber(accountNumber);
        if (account != null && account instanceof Withdraw) {
            return ((Withdraw) account).withdraw(amount);
        } else {
            System.out.println("Account not found or does not support withdrawal.");
            return false;
        }
    }
    @Override
    public void displayInformation() {
//      for (int a=0; a< . ;a++){
        DecimalFormat df = new DecimalFormat("#,### đ");
        System.out.print(getCustomerId() + " |       ");
        System.out.print(getName() + " |    ");
        if (isPremiumAccount()) {
            System.out.print("Premium |   ");
        } else {
            System.out.print("Normal |   ");
        }
        System.out.println("Tổng số tiền: " + df.format(getBalance()));

        // Xuống dòng trước khi hiển thị danh sách tài khoản
        System.out.println();

        // Hiển thị thông tin các tài khoản của khách hàng
        // Kiểm tra xem danh sách tài khoản có rỗng không
        if (this.getAccounts().isEmpty()) {
            System.out.println("Khách hàng không có tài khoản.");
        } else {
            // Hiển thị thông tin các tài khoản của khách hàng
            for (int i = 0; i < this.getAccounts().size(); i++) {
                Account account = this.getAccounts().get(i);
                if (account instanceof LoanAccount) {
                    System.out.println((i + 1)+"    " + account.getAccountNumber()+" |         " +" LOAN    " + " |        " + df.format(account.getBalance()));
                } else if (account instanceof SavingsAccount) {
                    System.out.println((i + 1)+"    " + account.getAccountNumber()+" |         " + " SAVINGS "  + " |        " + df.format(account.getBalance()));
                }}
//          }
        }
    }


    /// t
    public boolean isAccountExisted(Account newAccount) {
        boolean isNewAccountExist = false;
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
                isNewAccountExist = true;
                System.out.println("Tài khoản đã tồn tại");
                break;

            }
        }

        // Nếu mã số tài khoản chưa tồn tại, thêm tài khoản mới vào danh sách tài khoản của khách hàng
        if (!isNewAccountExist) {
            getAccounts().add(newAccount);
            return true;
//            System.out.println("Thêm tài khoản thành công");
        } else {
            System.out.println("Mã số tài khoản đã tồn tại. Không thể thêm tài khoản mới.");
            return false;
        }
    }
}
