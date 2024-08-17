package vn.funix.FX38455.java.asm02.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }


    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean isPremiumAccount(){
       for (Account account : accounts){
           if (account.isPremiumAccount()){
               return true;
           }
       }
       return false;
    }

//public void addAccount(Account newAccount) {
//    boolean isNewAccountExist = false;
//    for (Account account : accounts) {
//        if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
//            isNewAccountExist = true;
//            System.out.println("Tài khoản đã tồn tại");
//            break;
//        }
//    }
//    if (!isNewAccountExist) {
//        System.out.println("Thêm tài khoản thành công");
//        accounts.add(newAccount);
//    }
//}
//public void addAccount(Account newAccount) {
//    for (Account account : accounts) {
//        if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
//            System.out.println("Tài khoản đã tồn tại");
//            return; // Dừng lại nếu tài khoản đã tồn tại
//        }
//    }
//    accounts.add(newAccount);
//    System.out.println("Thêm tài khoản thành công");
//}


    public void addAccount(Account newAccount) {
        // Kiểm tra xem mã số tài khoản đã tồn tại trong danh sách tài khoản của khách hàng hay chưa
        boolean isNewAccountExist = false;
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
                isNewAccountExist = true;
                System.out.println("Tài khoản đã tồn tại");
                break;
            }
        }

        // Nếu mã số tài khoản chưa tồn tại, thêm tài khoản mới vào danh sách tài khoản của khách hàng
        if (!isNewAccountExist) {
            accounts.add(newAccount);
//            System.out.println("Thêm tài khoản thành công");
        } else {
            System.out.println("Mã số tài khoản đã tồn tại. Không thể thêm tài khoản mới.");
        }
    }
    public double getBalance() {
        double totalBalance = 0;
        for (Account account : accounts) {
            totalBalance +=  account.getBalance();
        }
        return totalBalance;
    }

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
          if (accounts.isEmpty()) {
              System.out.println("Khách hàng không có tài khoản.");
          } else {
              // Hiển thị thông tin các tài khoản của khách hàng
              for (int i = 0; i < accounts.size(); i++) {
                  Account account = accounts.get(i);
                  System.out.println((i + 1) + "      " + account.getAccountNumber() + " |                " + df.format(account.getBalance()));
              }
//          }
      }
    }

///as4
// Phương thức rút tiền từ tài khoản
public boolean withdraw(String accountNumber, double amount) {
    for (Account account : accounts) {
        if (account.getAccountNumber().equals(accountNumber)) {
            return account.withdraw(amount);
        }
    }
    System.out.println("Số tài khoản không hợp lệ.");
    return false;
}

    // Phương thức chuyển tiền giữa 2 tài khoản
    public boolean transfer(String sourceAccountNumber, String destinationAccountNumber, double amount) {
        Account sourceAccount = null;
        Account destinationAccount = null;

        // Tìm tài khoản nguồn và tài khoản đích
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(sourceAccountNumber)) {
                sourceAccount = account;
            }
            if (account.getAccountNumber().equals(destinationAccountNumber)) {
                destinationAccount = account;
            }
        }

        // Kiểm tra xem các tài khoản có tồn tại không và thực hiện chuyển tiền
        if (sourceAccount != null && destinationAccount != null) {
            return sourceAccount.transfer(destinationAccount, amount);
        } else {
            System.out.println("Số tài khoản không hợp lệ.");
            return false;
        }
    }
}/////////