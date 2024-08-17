package vn.funix.FX38455.java.asm04.dao;

import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.models.Customer;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//File CustomerDao định nghĩa lớp trung gian thao tác với file để lấy
// dữ liệu và thêm mới, cập nhật dữ liệu
public class CustomerDao {
    public final static String FILE_PATH = "store/customers.dat";
//    Tạo phương thức void static save() để lưu danh sách
//    khách hàng vào file. Input là danh sách khách hàng.
    public static void save(List<Customer> customers) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, customers);
    }
//    Tạo phương thức static list() không có tham số đầu vào để lấy ra
//    danh sách khách hàng từ file. Output là danh sách khách hàng.
    public static List<Customer> list() {
            return BinaryFileService.readFile(FILE_PATH);
    }

    public static void displayCustomerById(String customerId) {
        List<Customer> customers = list();
        boolean found = false;

        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                System.out.println("Tên: " + customer.getName());
                System.out.println("Mã số khách hàng: " + customer.getCustomerId());
                System.out.println("Số tài khoản:");
                for (Account account : customer.getAccounts()) {
                    System.out.println("  - Số tài khoản: " + account.getAccountNumber() + ", Số dư: " + account.getBalance() + " đ");
                }
                System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                found = true;
                break; // Thoát khỏi vòng lặp sau khi tìm thấy khách hàng
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy khách hàng với mã số: " + customerId);
        }
    }

}
