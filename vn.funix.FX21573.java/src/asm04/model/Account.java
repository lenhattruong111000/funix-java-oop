package asm04.model;

import asm03.utils.Utils;
import asm04.dao.TransactionDao;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable {
    private static final long serialVersionUID =1L;
    private String accountNumber;
    private double balance;

    private String customerId;

    private List<Transaction> transactions;

    public Account(){
        this.transactions =new ArrayList<>();
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

    public boolean input(Scanner scanner){
        return false;
    }

    public void createTransaction(double amount, String time, boolean status, TransactionType transactionType){
        List<Transaction> currentTransactionData = TransactionDao.list();

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setStatus(status);
        transaction.setTime(time);
        transaction.setType(transactionType);
        transaction.setAccountNumber(getAccountNumber());

        currentTransactionData.add(transaction);
        TransactionDao.save(currentTransactionData);
    }

    public List<Transaction> getTransactions() {

        return TransactionDao.list().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(getAccountNumber()))
                .collect(Collectors.toList());
    }

    public void displayTransactionsList(){
        if (!getTransactions().isEmpty()){
            for (Transaction transaction: getTransactions()) {
                System.out.printf("[GD]  %6s | %-10s | %20s |  %s%n",
                        transaction.getAccountNumber(),
                        transaction.getType().toString(),
                        NumberFormat
                                .getCurrencyInstance(new Locale("vi","VN"))
                                .format(transaction.getAmount()),
                        transaction.getTime());
            }
        }

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
