package asm02.models;

import asm03.models.DigitalBank;
import asm03.models.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Account {
    private String accountNumber;
    private double balance;

    private String customerId;

    private List<Transaction> transactions;

    public Account(){
        this.transactions =new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Customer getCustomer(List<Customer> customers){

        for (Customer customer1:customers) {
            if (customer1.getCustomerId().equals(this.customerId)){
                Customer customer= customer1;
                return customer;
            }
        }
        return null;
    }

    public boolean input(){
        return false;
    }

    public void createTransaction(double amount, String time, boolean status, String transactionType){

    }
    public void displayTransactionsList(){

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public  boolean isValidAccountNumber(String accountNumber){
        String regex ="^[0-9]{6}$";
        if (accountNumber.matches(regex)) return true;
        return false;
    }

    public void setAccountNumber(String accountNumber) {

        if (isValidAccountNumber(accountNumber))
            this.accountNumber = accountNumber;

    }

    public boolean isPremium(){
        // balance >=10.000.000vnd ==> premium
        if(this.balance>=10000000){
            return true;
        }
        return false;
    }

    public boolean isValidBalance(double balance){
        if (balance>=50000){
            return true;
        }
        return false;
    }

    public boolean isValidBalance(String balance){
        String regex ="^[0-9]+$";
        if (Double.parseDouble(balance)>=50000 && balance.matches(regex)){
            return true;
        }
        return false;
    }

    public  Account findAccount(String accountNumer){

        return null;

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        String balanceNf =  NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(balance);
        return  accountNumber + " | \t\t\t" + balanceNf ;
    }

}
