package vn.funix.FX38455.java.asm03.java;

import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm03.models.LoanAccount;
import vn.funix.FX38455.java.asm03.models.SavingsAccount;

import static org.junit.Assert.*;

public class LoanAccountTest {
    private LoanAccount loanAccount;
    @Before
    public void setUp() throws Exception {
        loanAccount = new LoanAccount();
        loanAccount.setBalance(0);
        loanAccount.setAccountNumber("123456");
    }

    @Test
    public void log() {
    }

    @Test
    public void withdraw() {
        assertTrue(loanAccount.withdraw(50_000_000));
        assertFalse(loanAccount.withdraw(100_000_000)); // kh dc rut >100tr(tinh ca PHI + VAT)
    }

    @Test
    public void isAccepted() {
        assertFalse(loanAccount.withdraw(123456)); // rut phai la boi so 10
    }

    // Test getFee
    @Test
    public void testGetFee() {
        assertEquals(5000, loanAccount.getFee(100000),0);// tk normal
        LoanAccount loanAccount2 = new LoanAccount(); // tk Premium
        loanAccount2.setBalance(20_000_000);
        loanAccount2.setAccountNumber("123456");
        assertEquals(1000, loanAccount2.getFee(100000),0);
    }

}