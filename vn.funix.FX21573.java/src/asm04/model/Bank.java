package asm04.model;

import asm01.Asm01;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {

    private final String bankId;

    private  String bankName;
    private final List<Customer> customers;


    public Bank(String bankName){
        this.bankId =String.valueOf(UUID.randomUUID());
        this.customers =new ArrayList<Customer>();
        this.bankName =bankName;
    }
    public Bank(){
        this.bankId =String.valueOf(UUID.randomUUID());
        this.customers =new ArrayList<Customer>();
    }

    public String getBankName() {
        return bankName;
    }

    public Customer findCustomer(String customerId){
        for (Customer customer: customers) {
            if (customer.getCustomerId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }
    public boolean addCustomer(Customer newCustomer){
        if (findCustomer(newCustomer.getCustomerId())==null){
            customers.add(newCustomer);
        }
        return false;
    }

    public boolean isCustomerExisted(String customerId){
        if(findCustomer(customerId)!=null)
            return true;
        return false;
    }

    public List<Account> getAllAccounts(){
        List<Account> allAccounts = new ArrayList<Account>();
        for (Customer customer: customers) {

            if (customer.getAccounts()!=null){
                for (Account account: customer.getAccounts()) {
                    if(account!=null){
                        allAccounts.add(account);
                    }
                }
            }
        }
        return  allAccounts;
    }

    public  boolean isAccountExisted(String accountNumber){
        if (getAllAccounts()!= null){
            for (Account account: getAllAccounts()) {
                if(account.getAccountNumber().equals(accountNumber)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAccount(String customerId, Account account ){
        if (isCustomerExisted(customerId)){
            Customer customer = findCustomer(customerId);
            customer.addAccount(account);
            return true;
        }
        return false;
    }

    public List<Customer> searchCustomerByName(String customerName){
        String regex ="[a-zA-Z]*"+customerName.toLowerCase()+"[a-zA-Z]*";
        List<Customer> searchList = new ArrayList<Customer>();
        for (Customer customer : customers){
            String[] name = customer.getName().split(" ");
            for (int i = 0; i < name.length ; i++) {
                //System.out.println(i+": "+ name[i]);
                if(name[i].toLowerCase().matches(regex)){
                    searchList.add(customer);
                }
            }


        }
        return searchList;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean isValidCustomerId( String customerId){
        if(Asm01.isValidIdNum(customerId)){
            return true;
        }
        return false;
    }
}
