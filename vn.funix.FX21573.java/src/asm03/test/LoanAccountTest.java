package asm03.test;

import asm03.models.DigitalBank;
import asm03.models.DigitalCustomer;
import asm03.models.LoanAccount;
import asm03.models.SavingsAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoanAccountTest {

    private DigitalBank bank;
    private DigitalCustomer customer;

    private LoanAccount account;
    @Before
    public void setup(){
        bank =new DigitalBank();
        customer =new DigitalCustomer();

        customer.setCustomerId("001200123456");
        customer.setName("Truong");
        bank.addCustomer(customer);

        account = new LoanAccount();
        account.setAccountNumber("123456");
        account.setBalance(500000000);

        bank.addAccount("001200123456", account);

    }

    @Test
    public void withdraw() {
        //assertTrue(account.withdraw(100000000));
        //assertFalse(account.withdraw(200000000));
        //assertFalse(account.withdraw(5000));

    }

    @Test
    public void isAccepted() {
        //assertTrue(account.isAccepted(100000000));
        //assertFalse(account.isAccepted(200000000));
        assertFalse(account.isAccepted(5000));

    }

    @Test
    public void getFee() {
        //account.withdraw(10000000);
        //assertEquals(100000, account.getFee(10000000),0.01);

        LoanAccount account1 = new LoanAccount();
        account1.setAccountNumber("234567");
        account1.setBalance(9999999);

        bank.addAccount("001200123456", account);

        account1.withdraw(500000);
        assertEquals(25000, account1.getFee(500000),0.01);

    }
}