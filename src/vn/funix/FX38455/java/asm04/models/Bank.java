package vn.funix.FX38455.java.asm04.models;

import vn.funix.FX38455.java.asm04.dao.AccountDao;
import vn.funix.FX38455.java.asm04.dao.CustomerDao;
import vn.funix.FX38455.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bankId;
    private String bankName;

    public Bank(String bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public Bank() {
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return bankId + "," + bankName;
    }


    public void showCustomers() {
        List<Customer> customers = CustomerDao.list() ;

        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào trong tệp.");
        } else {
            for (Customer customer : customers) {
                customer.displayInformation();
                System.out.println("-----------------------------");
            }
        }
    }

    public static boolean isAccountExits(String accountNumber) {
        List<Customer> customers = CustomerDao.list();

        for (Customer customer : customers) {
            List<Account> customerAccounts = customer.getAccounts();
            if (customerAccounts
                    .stream()
                    .anyMatch(account -> account.getAccountNumber().equals(accountNumber))) {
                return true;
            }
        }
        return false;
    }

    // Phương thức kiểm tra xem một khách hàng có tồn tại trong danh sách hay không dựa trên mã khách hàng
    public boolean isCustomerExist(List<Customer> customers, String customerId) {
        return customers
                .stream()
                .anyMatch(customer -> customer.getCustomerId().equals(customerId));
    }

//    addCustomers để xử lý thêm khách hàng từ file
    public void addCustomers(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Tệp không tồn tại: " + fileName);
            return;
        }

        // Đọc danh sách khách hàng từ file nhị phân
        List<Customer> existingCustomers = CustomerDao.list();
        List<Customer> newCustomers = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String customerId = parts[0];
                    String name = parts[1];
                    if (isCustomerExist(existingCustomers, customerId)) {
                        System.out.println("Khách hàng " + customerId + " đã tồn tại, không thể thêm.");
                    } else {
                        try {
                            Customer customer = new Customer(customerId, name);
                            newCustomers.add(customer);
                        } catch (CustomerIdNotValidException e) {
                            System.out.println("Lỗi khi thêm khách hàng: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Dòng không hợp lệ: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không thể mở tệp văn bản: " + e.getMessage());
            return;
        }

        // Ghi danh sách khách hàng vào file nhị phân
        existingCustomers.addAll(newCustomers);
        BinaryFileService.writeFile("store/customers.dat", existingCustomers);


    }


}
