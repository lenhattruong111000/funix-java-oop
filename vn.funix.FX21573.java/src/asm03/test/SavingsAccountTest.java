package asm03.test;

import asm03.models.DigitalBank;
import asm03.models.DigitalCustomer;
import asm03.models.SavingsAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    private DigitalBank bank;
    private DigitalCustomer customer;

    private SavingsAccount account;
    @Before
    public void setup(){
        bank =new DigitalBank();
        customer =new DigitalCustomer();

        customer.setCustomerId("001200123456");
        customer.setName("Truong");
        bank.addCustomer(customer);

        account = new SavingsAccount();
        account.setAccountNumber("123456");
        account.setBalance(10000000);

        bank.addAccount("001200123456", account);

    }

    @Test
    public void withdraw() {

        //assertTrue(account.withdraw(500000));
        //(account.withdraw(5000));
        assertFalse(account.withdraw(50000000));

    }

    @Test
    public void isAccepted() {
        //assertTrue(account.isAccepted(5000000));
        //assertFalse(account.isAccepted(5000));
        assertFalse(account.isAccepted(50000000));


    }
}