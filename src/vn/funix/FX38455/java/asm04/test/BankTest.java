package vn.funix.FX38455.java.asm04.test;

import org.junit.Before;
import org.junit.Test;
import vn.funix.FX38455.java.asm04.dao.CustomerDao;
import vn.funix.FX38455.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.models.Bank;
import vn.funix.FX38455.java.asm04.models.Customer;
import vn.funix.FX38455.java.asm04.service.BinaryFileService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BankTest {

    private Bank bank;
    private Account account;


    @Before
    public void setUp() throws CustomerIdNotValidException {
        bank = new Bank("B001", "Example Bank");
        account = new Account("123456", 2000000.0);


    }

    @Test
    public void testGetBankId() {
        assertEquals("B001", bank.getBankId());
    }

    @Test
    public void testGetBankName() {
        assertEquals("Example Bank", bank.getBankName());
    }

    @Test
    public void testToString() {
        assertEquals("B001,Example Bank", bank.toString());
    }


    @Test
    public void testIsAccountExists_WhenAccountDoesNotExist() {
        assertFalse(Bank.isAccountExits("999999"));
    }




}