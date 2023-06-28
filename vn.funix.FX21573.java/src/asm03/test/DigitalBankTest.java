package asm03.test;

import asm02.models.Account;
import asm02.models.Customer;
import asm03.models.DigitalBank;
import asm03.models.DigitalCustomer;
import asm03.models.LoanAccount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class DigitalBankTest {
    private  DigitalBank bank;
    private DigitalCustomer customer;
    @Before
    public void setup(){
        bank =new DigitalBank();
        customer =new DigitalCustomer();

    }
    @Test
    public void isCustomerExisted() {
        customer.setName("truong");
        customer.setCustomerId("001200123456");
        //assertFalse(bank.isCustomerExisted("001200123456"));
        bank.addCustomer(customer);
        assertTrue(bank.isCustomerExisted("001200123456"));

    }


    @Test
    public void isAccountExisted() {
        Account account =new Account();
        account.setAccountNumber("123456");
        account.setBalance(1000000);
        //assertFalse(bank.isAccountExisted("123456"));
        customer.addAccount(account);
        bank.addCustomer(customer);
        assertTrue(bank.isAccountExisted("123456"));
    }
}