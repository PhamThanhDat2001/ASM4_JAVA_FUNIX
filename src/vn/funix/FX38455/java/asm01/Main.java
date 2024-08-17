package vn.funix.FX38455.java.asm01;


import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
   static Scanner scanner = new Scanner(System.in);

    static Random random = new Random();
    static int maxacthuc;
    static int xacnhan;
    static String xacnhanHard;
    static String cccd;
    private static void menu() {
        System.out.println("| 1. Kiểm tra noi sinh");
        System.out.println("| 2. Kiểm tra năm sinh, giới tính");
        System.out.println("| 3. Kiểm tra số ngẫu nhiên");
        System.out.println("| 0. Thoát ");
    }

    // kiểm tra mã xác thực đúng hay chưa
//    public static void maXacthuc(){
//         maxacthuc = random.nextInt(900) + 100;
//        System.out.println("Ma xac thuc: " + maxacthuc);
//        System.out.print("Nhap ma xac thuc: ");
//         xacnhan = getValidChoice();
//        while (xacnhan != maxacthuc){
//            System.out.println("Ma xac thuc khong dung. " +
//                    "Vui long thu lai: " + maxacthuc);
//            System.out.print("Nhap ma xac thuc: ");
//            xacnhan = getValidChoice();
//        }
//    }

    // nếu mã xác thực đúng thì kiểm tra cccd
    public static void inputCccd(){
     if (xacnhan == maxacthuc){
         boolean flag = true;
         System.out.print("Nhap CCCD: " );

         cccd = scanner.next();
         // nếu khác 0-9 và 12 ký tự
         while (!cccd.matches("[0-9]+") || cccd.length() != 12) {
             System.out.println("So CCCD khong hop le.");
             System.out.print("Vui long nhap lai hoac 'no' de thoat: ");
             cccd = scanner.next();
             if (cccd.equals("no")) {
                 break;
             }
         }
         while (flag && cccd.length() == 12){
             menu();
             System.out.print("Chọn chức năng: ");

             int choice = getValidChoice();
             switch (choice) {
                 case 1:
                     checkPlace();
                     break;
                 case 2:
                     checkYearOfBirthAndGender();
                     break;
                 case 3:
                     checkRandomNumbers();
                     break;
                 case 0:
                     flag = false;
                     break;
                 default:
                     System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                     break;
             }
         }
     }
    }

    // phương thức kiểm tra nơi sinh
    public static void checkPlace(){
        String maTinhTuCccd = cccd.substring(0,3);
        if (maTinh.containsKey(maTinhTuCccd)){
            System.out.println("Tinh: " + maTinh.get(maTinhTuCccd));
        }
        else {
            System.out.println("Mã tỉnh không hợp lệ hoặc không tồn tại.");
        }
    }

    // phương thức kiểm tra năm sinh, giới tính
    public static void checkYearOfBirthAndGender(){
        String gioiTinhTuCccd = cccd.substring(3,4);
        String namSinhTuCccd = cccd.substring(4,6);
        if(gioiTinhTuCccd.equals("0") || gioiTinhTuCccd.equals("2") ||  gioiTinhTuCccd.equals("4")
                ||  gioiTinhTuCccd.equals("6") ||  gioiTinhTuCccd.equals("8") ){
            System.out.println("Gioi tinh: Nam");
            switch (gioiTinhTuCccd) {
                case "0":
                    System.out.println("Nam sinh: 19" + namSinhTuCccd);
                    break;
                case "2":
                    System.out.println("Nam sinh: 20" + namSinhTuCccd);
                    break;
                case "4":
                    System.out.println("Nam sinh: 21" + namSinhTuCccd);
                    break;
                case "6":
                    System.out.println("Nam sinh: 22" + namSinhTuCccd);
                    break;
                case "8":
                    System.out.println("Nam sinh: 23" + namSinhTuCccd);
                    break;
            }
        }else if(gioiTinhTuCccd.equals("1") || gioiTinhTuCccd.equals("3") ||  gioiTinhTuCccd.equals("5")
                ||  gioiTinhTuCccd.equals("7") ||  gioiTinhTuCccd.equals("9") ){
            System.out.println("Gioi tinh: Nu");
            switch (gioiTinhTuCccd) {
                case "1":
                    System.out.println("Nam sinh: 19" + namSinhTuCccd);
                    break;
                case "3":
                    System.out.println("Nam sinh: 20" + namSinhTuCccd);
                    break;
                case "5":
                    System.out.println("Nam sinh: 21" + namSinhTuCccd);
                    break;
                case "7":
                    System.out.println("Nam sinh: 22" + namSinhTuCccd);
                    break;
                case "9":
                    System.out.println("Nam sinh: 23" + namSinhTuCccd);
                    break;
            }
        }

    }

    //phương thức hiển thị số ngẫu nhiên
    public static void checkRandomNumbers(){
        System.out.println("Số ngẫu nhiên: "+ cccd.substring(6,12));

    }

    // Tạo một Map chứa mã tỉnh và tên tỉnh
    static Map<String, String> maTinh = new HashMap<>();
    static {
        maTinh.put("001", "Hà Nội");
        maTinh.put("002", "Hà Giang");
        maTinh.put("004", "Cao Bằng");
        maTinh.put("006", "Bắc Kạn");
        maTinh.put("008", "Tuyên Quang");
        maTinh.put("010", "Lào Cai");
        maTinh.put("011", "Điện Biên");
        maTinh.put("012", "Lai Châu");
        maTinh.put("014", "Sơn La");
        maTinh.put("015", "Yên Bái");
        maTinh.put("017", "Hòa Bình");
        maTinh.put("019", "Thái Nguyên");
        maTinh.put("020", "Lạng Sơn");
        maTinh.put("022", "Quảng Ninh");
        maTinh.put("024", "Bắc Giang");
        maTinh.put("025", "Phú Thọ");
        maTinh.put("026", "Vĩnh Phúc");
        maTinh.put("027", "Bắc Ninh");
        maTinh.put("030", "Hải Dương");
        maTinh.put("031", "Hải Phòng");
        maTinh.put("033", "Hưng Yên");
        maTinh.put("034", "Thái Bình");
        maTinh.put("035", "Hà Nam");
        maTinh.put("036", "Nam Định");
        maTinh.put("037", "Ninh Bình");
        maTinh.put("038", "Thanh Hóa");
        maTinh.put("040", "Nghệ An");
        maTinh.put("042", "Hà Tĩnh");
        maTinh.put("044", "Quảng Bình");
        maTinh.put("045", "Quảng Trị");
        maTinh.put("046", "Thừa Thiên Huế");
        maTinh.put("048", "Đà Nẵng");
        maTinh.put("049", "Quảng Nam");
        maTinh.put("051", "Quảng Ngãi");
        maTinh.put("052", "Bình Định");
        maTinh.put("054", "Phú Yên");
        maTinh.put("056", "Khánh Hòa");
        maTinh.put("058", "Ninh Thuận");
        maTinh.put("060", "Bình Thuận");
        maTinh.put("062", "Kon Tum");
        maTinh.put("064", "Gia Lai");
        maTinh.put("066", "Đắk Lắk");
        maTinh.put("067", "Đắk Nông");
        maTinh.put("068", "Lâm Đồng");
        maTinh.put("070", "Bình Phước");
        maTinh.put("072", "Tây Ninh");
        maTinh.put("074", "Bình Dương");
        maTinh.put("075", "Đồng Nai");
        maTinh.put("077", "Bà Rịa - Vũng Tàu");
        maTinh.put("079", "Hồ Chí Minh");
        maTinh.put("080", "Long An");
        maTinh.put("082", "Tiền Giang");
        maTinh.put("083", "Bến Tre");
        maTinh.put("084", "Trà Vinh");
        maTinh.put("086", "Vĩnh Long");
        maTinh.put("087", "Đồng Tháp");
        maTinh.put("089", "An Giang");
        maTinh.put("091", "Kiên Giang");
        maTinh.put("092", "Cần Thơ");
        maTinh.put("093", "Hậu Giang");
        maTinh.put("094", "Sóc Trăng");
        maTinh.put("095", "Bạc Liêu");
        maTinh.put("096", "Cà Mau");
    }

    // Phương thức để lấy lựa chọn hợp lệ từ người dùng xem phải số hay kh
    public static int getValidChoice() {
        int choice ;
        while (true) {
            // try-catch nếu mà chuyển được sang số thì vào try còn không thì vao catch
            // có chữ tức là kh chuyển dc sang số
            try {
                choice = Integer.parseInt(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Khong hop le. Vui long nhap so");
                System.out.print("Nhap lai: ");
            }
        }
//        System.out.println(choice);
        return choice;
    }

    // Chọn chế độ bảo mật
    public static void selectSecurityMode(){
        int choice;
        do {
                System.out.println("Chọn chế độ:");
                System.out.println("1. EASY (3 ký tự số)");
                System.out.println("2. HARD (6 ký tự số và chữ)");
                System.out.print("Chế độ: ");
            choice = getValidChoice();

                switch (choice) {
                    case 1:
                        generateEasyPassword();
                        break;
                    case 2:
                        generateHardPassword();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                        System.out.println();
                        break;
                }
        }while (choice != 1 && choice != 2);
    }

    // Chế độ dễ
    public static void generateEasyPassword() {
        maxacthuc = random.nextInt(900) + 100;
        System.out.println("Ma xac thuc: " + maxacthuc);
        System.out.print("Nhap ma xac thuc: ");
        xacnhan = getValidChoice();
        while (xacnhan != maxacthuc){
            System.out.println("Ma xac thuc khong dung. " +
                    "Vui long thu lai: " + maxacthuc);
            System.out.print("Nhap ma xac thuc: ");
            xacnhan = getValidChoice();
        }
    }

    // Chế độ khó
    public static void generateHardPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder maxacthucHard = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(chars.length());
            maxacthucHard.append(chars.charAt(randomIndex));
        }

        System.out.println("Ma xac thuc: " + maxacthucHard);
        System.out.print("Nhap ma xac thuc: ");
        xacnhanHard = scanner.next();
        while (!xacnhanHard.contentEquals(maxacthucHard)){
            System.out.println("Ma xac thuc khong dung. " +
                    "Vui long thu lai: " + maxacthucHard);
            System.out.print("Nhap ma xac thuc: ");
            xacnhanHard = scanner.next();
        }
    }

    public static void main(String[] args) {

        int choice;
        do {
            // In tiêu đề
            System.out.println("+--------------------------------+");
            System.out.println("| NGAN HANG SO | FX123456@v1.0.0 |");
            System.out.println("+--------------------------------+");

            // In menu
            System.out.println("| 1. Nhap CCCD                   |");
            System.out.println("| 0. Thoat                       |");
            System.out.println("+--------------------------------+");

            // Nhập chức năng
            System.out.print("Chuc nang: ");
            choice = getValidChoice();

            // Xử lý lựa chọn
            switch (choice) {
                case 1:
                    selectSecurityMode();
                    inputCccd();
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                    System.out.println();
                    break;
            }
        } while (choice != 0 && choice != 1);

        scanner.close();
    }
}
