package vn.funix.FX38455.java.asm04.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileService {
//    <T> ,đọc các đối tượng từ một tệp và trả về một danh sách các đối tượng đó.
    public static <T> List<T> readFile(String fileName) {
//        Là một danh sách để chứa các đối tượng được đọc từ tệp.
        List<T> objects = new ArrayList<>();
        try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
//            Là cờ để kiểm tra khi đạt đến cuối tệp
            boolean eof = false;
            while(!eof) {
                try {
//             Gọi file.readObject() để đọc một đối tượng từ tệp và thêm nó vào danh sách objects.
                    T object = (T) file.readObject();
                    objects.add(object);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (EOFException e) {
//            OFException: Kết thúc việc đọc và trả về danh sách trống.
            return new ArrayList<>();
        } catch (IOException io) {
            System.out.println("IO Exception" + io.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        return objects;
    }

//     (<T>) có nhiệm vụ ghi các đối tượng từ danh sách vào tệp.
//List<T> objects Là danh sách các đối tượng cần được ghi vào tệp.
    public static <T> void writeFile(String fileName, List<T> objects) {
        try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (T object : objects) {
                file.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
