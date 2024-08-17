package vn.funix.FX38455.java.asm04.dao;
import vn.funix.FX38455.java.asm04.models.Transaction;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.List;

public class TransactionDao {
    public final static String FILE_PATH = "store/transactions.dat";

    //    Tạo phương thức void static save() để lưu danh sách
//    khách hàng vào file. Input là danh sách khách hàng.
    public static void save(List<Transaction> transactions) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, transactions);
    }

    //    Tạo phương thức static list() không có tham số đầu vào để lấy ra
//    danh sách khách hàng từ file. Output là danh sách khách hàng.
    public static List<Transaction> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }
}
