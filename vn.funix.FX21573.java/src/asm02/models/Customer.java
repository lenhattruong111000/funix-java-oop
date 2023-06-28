package asm02.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Customer extends User {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public Customer(){
        accounts =new ArrayList<Account>();
    }

    public boolean isPremium(){
        for (Account account: this.accounts) {
            if (account.isPremium())
                return true;
        }
        return false;
    }

    // find account by AccountNumer
    public Account findAccount(String accountNumber){
        if (accounts!=null){
            for ( Account account: accounts) {
                if (account.getAccountNumber().equals(accountNumber))
                    return account;
            }
        }
        return null;
    }

    public boolean addAccount(Account account){
        if (findAccount(account.getAccountNumber())==null){
            accounts.add(account);
            return true;
        }
        return false;
    }

    public String customerStyle(boolean isPremium){
        if (isPremium) return "Premium";
        return "Normal";
    }

    //get total balance of all accounts of a customer
    public double totalBalanceOfAllAccounts(){
        double totalBalance=0.0;
        for (Account account: this.accounts) {
           totalBalance+= account.getBalance();
        }
        return totalBalance;
    }
    public void displayInformation(){
        System.out.println(super.getCustomerId()+" | \t"
                + super.getName()+"\t | \t"
                + customerStyle(isPremium())+"\t | \t"
                + NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(totalBalanceOfAllAccounts()));

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i+1)+"\t"+accounts.get(i).getAccountNumber()
                    +" | \t\t\t\t\t\t\t\t\t"
                    +NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(accounts.get(i).getBalance()));
        }
    }
}
