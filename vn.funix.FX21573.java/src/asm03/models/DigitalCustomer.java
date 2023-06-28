package asm03.models;

import asm02.models.Customer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class DigitalCustomer extends Customer {

    @Override
    public void displayInformation(){

        System.out.printf("%12s | %-24s | %-7s | %20s%n",super.getCustomerId(),super.getName(),customerStyle(isPremium()),NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(new BigDecimal(totalBalanceOfAllAccounts())));

//        System.out.println(super.getCustomerId()+" | \t"+ super.getName()+"\t | \t"
//                + customerStyle(isPremium())+"\t | \t"
//                + NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(new BigDecimal(totalBalanceOfAllAccounts())));

        for (int i = 0; i < super.getAccounts().size(); i++) {

            System.out.printf("%-4d%-8s | %-24s | %30s%n",(i+1),super.getAccounts().get(i).getAccountNumber(),super.getAccounts().get(i).getClass().getSimpleName().replace("Account","").toUpperCase(),NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(new BigDecimal(super.getAccounts().get(i).getBalance())));
//            System.out.println((i+1)+"\t"+super.getAccounts().get(i).getAccountNumber()+"\t | \t"
//                    + super.getAccounts().get(i).getClass().getSimpleName().replace("Account","").toUpperCase() +"\t |\t\t\t\t"
//                    + NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(new BigDecimal(super.getAccounts().get(i).getBalance())));
        }
    }
}
