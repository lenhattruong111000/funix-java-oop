package asm03.test;

import asm02.models.Account;
import asm03.models.DigitalBank;
import asm03.models.DigitalCustomer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DigitalCustomerTest {

    private DigitalBank bank;
    private DigitalCustomer customer;
    @Before
    public void setup(){
        bank =new DigitalBank();
        customer =new DigitalCustomer();

    }

    @Test
    public void isPremium() {
        Account account =new Account();
        account.setAccountNumber("123456");
        account.setBalance(9999999);
        //customer.addAccount(account);
        //assertFalse(customer.isPremium());

        account.setBalance(10000000);
        customer.addAccount(account);
        assertTrue(customer.isPremium());
    }

    @Test
    public void totalBalanceOfAllAccounts() {
        Account account = new Account();
        Account account1 =new Account();

        account.setAccountNumber("123456");
        account.setBalance(10000000);
        customer.addAccount(account);

        account1.setAccountNumber("234567");
        account1.setBalance(5000000);
        customer.addAccount(account1);

        assertEquals(15000000,customer.totalBalanceOfAllAccounts(), 0.01);

    }
}