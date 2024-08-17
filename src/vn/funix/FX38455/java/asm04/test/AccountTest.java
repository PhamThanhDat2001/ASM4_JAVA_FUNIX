package vn.funix.FX38455.java.asm04.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm04.dao.TransactionDao;
import vn.funix.FX38455.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.models.Customer;
import vn.funix.FX38455.java.asm04.models.TransactionType;

import static org.junit.Assert.*;

public class AccountTest {
    private Account account;

    @Before
    public void setUp() throws CustomerIdNotValidException {
        // Khởi tạo một tài khoản mới với số dư
        account = new Account("123456", 2000000.0);

    }
    @Test
    public void testGetCustomerId() {
        // Kiểm tra phương thức getAccountNumber() có trả về đúng số tài khoản không
        assertEquals("123456", account.getAccountNumber());
    }


    @Test
    public void testGetAccountNumber() {
        // Kiểm tra phương thức getAccountNumber() có trả về đúng số tài khoản không
        assertEquals("123456", account.getAccountNumber());
    }

    @Test
    public void testIsPremiumAccount() {
        // Kiểm tra phương thức isPremiumAccount() có đánh giá đúng tài khoản Premium không
        assertFalse(account.isPremiumAccount()); // 10tr mới là Premium
    }

    @Test
    public void testGetBalance() {
        // Kiểm tra phương thức getBalance() có trả về đúng số dư của tài khoản không
        assertEquals(2000000.0, account.getBalance(), 0.01);
    }



    @Test
    public void testIsAccepted() {
        // Kiểm tra phương thức isAccepted() với số tiền rút là 500000
        assertTrue(account.isAccepted(500000));
    }

}