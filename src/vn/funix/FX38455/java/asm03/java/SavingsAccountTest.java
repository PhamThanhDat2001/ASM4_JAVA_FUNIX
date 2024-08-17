package vn.funix.FX38455.java.asm03.java;

import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm03.models.SavingsAccount;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    private SavingsAccount savingsAccount;
    @Before
    public void setUp() throws Exception {
        savingsAccount = new SavingsAccount();
        savingsAccount.setBalance(6000000);
        savingsAccount.setAccountNumber("123456");
    }

    @Test
    public void log() {
    }

    @Test
    public void withdraw() {
        assertTrue(savingsAccount.withdraw(500000)); // lon hon 50k
        assertFalse(savingsAccount.withdraw(6000000)); // kh du tien tk
        assertFalse(savingsAccount.withdraw(123456)); // rut phai la boi so 10

    }

    @Test
    public void isAccepted() {
        assertFalse(savingsAccount.withdraw(50000));
        assertFalse(savingsAccount.withdraw(5000001)); // tk normal kh dc ru >5tr
    }

    @Test
    public void addAccount() {
    }

    // Test getFee
    @Test
    public void testGetFee() {
        assertEquals(0, savingsAccount.getFee(100000),0);
    }
}