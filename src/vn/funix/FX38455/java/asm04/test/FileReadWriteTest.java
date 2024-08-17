package vn.funix.FX38455.java.asm04.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileReadWriteTest {
    private static final String TEST_FILE_NAME = "test.dat";

    @Test
    public void testBinaryFileServiceReadWrite() {
        // Ghi dữ liệu vào file
        List<String> dataToWrite = new ArrayList<>();
        dataToWrite.add("Data 1");
        dataToWrite.add("Data 2");
        BinaryFileService.writeFile(TEST_FILE_NAME, dataToWrite);

        // Đọc dữ liệu từ file
        List<String> dataRead = BinaryFileService.readFile(TEST_FILE_NAME);
        // Kiểm tra xem dữ liệu đã được đọc có khớp với dữ liệu đã ghi hay không
        assertEquals(dataToWrite, dataRead);
    }

    @Test
    public void testBinaryFileServiceReadWriteWithEmptyFile() {
        // Ghi dữ liệu vào file rỗng
        List<String> dataToWrite = new ArrayList<>();
        BinaryFileService.writeFile(TEST_FILE_NAME, dataToWrite);

        // Đọc dữ liệu từ file rỗng
        List<String> dataRead = BinaryFileService.readFile(TEST_FILE_NAME);
        // Kiểm tra xem dữ liệu đã được đọc có phải là danh sách rỗng hay không
        assertEquals(0, dataRead.size());
    }


    @After
    public void tearDown() {
        // Xóa file test.txt sau khi hoàn thành
        File file = new File(TEST_FILE_NAME);
        if (!file.delete()) {
            System.err.println("Không thể xóa file test.");
        }
    }
}
