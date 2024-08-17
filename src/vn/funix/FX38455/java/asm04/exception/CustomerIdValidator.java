package vn.funix.FX38455.java.asm04.exception;

public class CustomerIdValidator {
    public static boolean validateCustomerId(String customerId) {
        // Logic xác thực cơ bản: Kiểm tra xem customerId có phải là null và có độ dài hợp lệ hay không
        return customerId != null && customerId.length() > 0 && customerId.matches("\\d{12}");
    }
}