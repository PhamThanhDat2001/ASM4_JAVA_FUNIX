package vn.funix.FX38455.java.asm04.dao;

import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AccountDao {
    public final static String FILE_PATH = "store/accounts.dat";
    //    Tạo phương thức void static save() để lưu danh sách
//    khách hàng vào file. Input là danh sách khách hàng.
    public static void save(List<Account> accounts) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }
    //    Tạo phương thức static list() không có tham số đầu vào để lấy ra
//    danh sách khách hàng từ file. Output là danh sách khách hàng.
    public static List<Account> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

//  phương thức  để cập nhật số dư cho tài khoản
//public static void update(Account editAccount) throws IOException {
//    List<Account> accounts = list();
//    boolean hasExist = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
//    List<Account> updatedAccounts;
//    if (!hasExist) {
//        updatedAccounts = new ArrayList<>(accounts);
//        updatedAccounts.add(editAccount);
//    } else {
//        updatedAccounts = new ArrayList<>();
//        for (Account account: accounts){
//            if (account.getAccountNumber().equals(editAccount.getAccountNumber())){
//                updatedAccounts.add(editAccount);
//            }else {
//                updatedAccounts.add(account);
//            }
//        }
//    }
//    save(updatedAccounts);
//}
private static final int MAX_THREAD = 4; // Số luồng tối đa được sử dụng

    public static void update(Account editAccount) throws IOException {
        List<Account> accounts = list();
        AtomicBoolean hasExist = new AtomicBoolean(false);

        // Tạo một ExecutorService với số luồng tối đa được sử dụng
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);

        // Duyệt qua từng tài khoản trong danh sách và kiểm tra đồng thời
        for (Account account : accounts) {
            executorService.execute(() -> {
                if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                    hasExist.set(true);
                }
            });
        }

        // Đợi cho tất cả các luồng hoàn thành
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Account> updatedAccounts;
        if (!hasExist.get()) {
            updatedAccounts = new ArrayList<>(accounts);
            updatedAccounts.add(editAccount);
        } else {
            updatedAccounts = new ArrayList<>();
            for (Account account : accounts) {
                if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                    updatedAccounts.add(editAccount);
                } else {
                    updatedAccounts.add(account);
                }
            }
        }
        save(updatedAccounts);
    }

}

