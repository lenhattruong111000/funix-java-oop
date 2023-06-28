package asm04.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DigitalCustomer extends Customer implements Serializable {


    private static final long serialVersionUID =1L;

    @Override
    public void displayInformation(){

        System.out.printf("%12s | %-33s | %-7s | %20s%n",
                super.getCustomerId(),
                super.getName(),
                customerStyle(isPremium()),
                NumberFormat.getCurrencyInstance(new Locale("vi","VN"))
                        .format(new BigDecimal(totalBalanceOfAllAccounts())));

        for (int i = 0; i < super.getAccounts().size(); i++) {

            System.out.printf("%-4d%-8s | %-33s | %30s%n",
                    (i+1),
                    super.getAccounts().get(i).getAccountNumber(),
                    super.getAccounts().get(i).getClass().getSimpleName()
                            .replace("Account","").toUpperCase(),
                    NumberFormat.getCurrencyInstance(new Locale("vi","VN"))
                            .format(new BigDecimal(super.getAccounts().get(i).getBalance())));

        }
    }
}
