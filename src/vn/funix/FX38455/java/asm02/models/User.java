package vn.funix.FX38455.java.asm02.models;

import java.util.Scanner;

public class User {
    private String name;
    private String customerId;
    static Scanner scanner = new Scanner(System.in);
    public User() {
//        customerId = "123456789012";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

//    public void setCustomerId(String customerId) {
//        Scanner scanner = new Scanner(System.in);
//        // Kiểm tra mã CCCD có hợp lệ không
//        while (!customerId.matches("[0-9]+") || customerId.length() != 12) {
//            System.out.println("Số CCCD không hợp lệ.");
//
//            System.out.print("Vui lòng nhập lại hoặc nhập 'no' để thoát: ");
//            String input = scanner.next();
//
//            // Kiểm tra người dùng có muốn thoát không
//            if (input.equals("no")) {
//                System.out.println("Đã hủy thao tác nhập CCCD. Thoát chương trình...");
//                System.exit(0);  // Thoát khỏi chương trình với mã trạng thái 0
//            } else {
//                customerId = input; //////////
//            }
//        }
//        // Nếu mã CCCD hợp lệ, cập nhật vào thuộc tính của đối tượng
//        this.customerId = customerId;
////        System.out.println("Đã thêm khách hàng " + this.customerId + " vào danh sách");
//    }

    public void setCustomerId(String customerId) {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            try {
                // Kiểm tra mã CCCD có hợp lệ không
                if (customerId.matches("[0-9]+") && customerId.length() == 12) {
                    isValid = true;
                } else {
                    // IllegalArgumentException là exep có đối số kh hợp lệ
                    throw new IllegalArgumentException("Số CCCD không hợp lệ.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                System.out.print("Vui lòng nhập lại hoặc nhập 'no' để thoát: ");
                String input = scanner.next();

                // Kiểm tra người dùng có muốn thoát không
                // equalsIgnoreCase kh phân biệt chữ hoa chữ thương
                if (input.equalsIgnoreCase("no")) {
                    System.out.println("Đã hủy thao tác nhập CCCD. Thoát chương trình...");
                    System.exit(0);  // Thoát khỏi chương trình với mã trạng thái 0
                } else {
                    customerId = input;
                }
            }
        }

        // Nếu mã CCCD hợp lệ, cập nhật vào thuộc tính của đối tượng
        this.customerId = customerId;
        // System.out.println("Đã thêm khách hàng " + this.customerId + " vào danh sách");
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
////////