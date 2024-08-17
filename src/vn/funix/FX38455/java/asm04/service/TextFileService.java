package vn.funix.FX38455.java.asm04.service;

import vn.funix.FX38455.java.asm04.models.Bank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//File TextFileService định nghĩa lớp thao tác với file text,
// người dùng có thể hiển thị và sửa đổi dữ liệu file text này.
public class TextFileService {
    public static final String COMMA_DELIMITER = ",";

    // Phương thức đọc tệp và trả về danh sách các danh sách chuỗi
    public static List<List<String>> readFile(String fileName) {
        List<List<String>> records = new ArrayList<>();

//        Sử dụng BufferedReader để đọc từng dòng trong tệp.
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Tách dòng thành các phần tử sử dụng dấu phân cách
                String[] values = line.split(COMMA_DELIMITER);
                // Thêm mảng các phần tử vào danh sách chính
                List<String> record = new ArrayList<>();
                for (String value : values) {
                    record.add(value);
                }
                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
//    hỗ trợ đọc và lưu danh sách các đối tượng Bank vào/ra từ tệp./
public static List<Bank> readBanksFromFile(String fileName) {
    List<Bank> banks = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(COMMA_DELIMITER);
            if (values.length == 2) {
                Bank bank = new Bank(values[0], values[1]);
                banks.add(bank);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return banks;
}

    public static void writeBanksToFile(String fileName, List<Bank> banks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Bank bank : banks) {
                bw.write(bank.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
