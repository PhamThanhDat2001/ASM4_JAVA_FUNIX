package vn.funix.FX38455.java.asm03.java;

import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm02.models.Account;
import vn.funix.FX38455.java.asm02.models.Customer;
import vn.funix.FX38455.java.asm03.models.DigitalBank;
import vn.funix.FX38455.java.asm03.models.DigitalCustomer;
import vn.funix.FX38455.java.asm03.models.LoanAccount;
import vn.funix.FX38455.java.asm03.models.SavingsAccount;

import static org.junit.Assert.*;

public class DigitalBankTest {
    private SavingsAccount savingsAccount;
    private LoanAccount loanAccount;
    private DigitalBank digitalBank;
    private DigitalCustomer customer;
    @Before
    public void setUp() throws Exception {
        savingsAccount = new SavingsAccount();
        loanAccount = new LoanAccount();
        digitalBank = new DigitalBank();

        savingsAccount.setAccountNumber("123456");
        savingsAccount.setBalance(20000000);

        loanAccount.setAccountNumber("654321");
        loanAccount.setBalance(0);

         customer = new DigitalCustomer();
        customer.setCustomerId("001215000001");
        customer.addAccount(savingsAccount);
        customer.addAccount(loanAccount);

        digitalBank.getCustomers().add(customer);

    }

    // Test Customer getCustomerById
    @Test
    public void getCustomerById() {
        assertNotNull(digitalBank.getCustomerById("001215000001")); // get ra xem co null kh
        assertNull(digitalBank.getCustomerById("001215000002"));
    }

    @Test
    public void addCustomer() {
        //        Thêm một khách hàng mới (customer1) và kiểm tra xem
//        khách hàng này có nằm trong danh sách khách hàng của ngân hàng hay không.
        digitalBank = new DigitalBank();
        digitalBank.addCustomer(customer);
        assertTrue(digitalBank.getCustomers().contains(customer));
    }

    // Test isAccountPremium
    @Test
    public void testIsAccountPremium() {
        assertTrue(savingsAccount.isPremiumAccount());
        assertFalse(loanAccount.isPremiumAccount());
    }

    // Test isCustomerExisted
    @Test
    public void testIsCustomerExisted() {
        assertTrue(digitalBank.isCustomerExisted("001215000001")); // tk da ton tai roi
        assertFalse(digitalBank.isCustomerExisted("001215000002"));// tk chua ton tai
    }

    // Test isAccountExisted method
    @Test
    public void testIsAccountExisted() {
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setBalance(0);
        Account account2 = new Account();
        account2.setAccountNumber("555555");
        account2.setBalance(0);
        assertFalse(customer.isAccountExisted(account)); // tk da ton tai
        assertTrue(customer.isAccountExisted(account2)); // tk chua ton tai
    }


    // Test getTotalAccountBalance method
    @Test
    public void testGetTotalAccountBalance() {
        // lay so du
        assertEquals(20000000, savingsAccount.getBalance(),0);
        assertEquals(0, loanAccount.getBalance(),0);
    }


    // Test validateAccount method
    @Test
    public void testValidateAccount() {
        assertTrue(digitalBank.validateAccount("123456"));
        assertFalse(digitalBank.validateAccount("123"));
        assertFalse(digitalBank.validateAccount("123abc"));
    }

    // Test isCustomerPremium method
    @Test
    public void testIsCustomerPremium() {
        assertTrue(digitalBank.getCustomerById("001215000001").isPremiumAccount());

    }

    // Test getAccountByAccountNumber method
    @Test
    public void testGetAccountByAccountNumber() {
        assertNotNull(customer.getAccountByNumber("123456"));
        assertNull(customer.getAccountByNumber("000000"));
    }

//    Test validateCustomerId method
    @Test
   public void testValidateCustomerId() {
        assertTrue(digitalBank.validateCustomerId("001215000001"));
        assertFalse(digitalBank.validateCustomerId("1232331"));
        assertFalse(digitalBank.validateCustomerId("001215000abc"));
    }
}