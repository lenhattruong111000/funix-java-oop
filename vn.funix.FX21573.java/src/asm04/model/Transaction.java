package asm04.model;

import asm03.utils.Utils;

import java.io.Serializable;
import java.util.UUID;

public class Transaction implements Serializable {

    private static final long serialVersionUID =1L;

    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    private TransactionType type;
    public Transaction(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String toString(){
        return "[GD]";
    }

    public void displayTransaction(){
        System.out.println(Utils.getDivider());
        System.out.printf("TRANG THAI: %26s%n", isStatus());
        System.out.printf("NGAY G/D: %28s%n", getTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Utils.formatBalance(getAmount()));
        //System.out.printf("PHI + VAT: %27s%n", Utils.formatBalance(getFee()));
        System.out.println(Utils.getDivider());
    }
}
