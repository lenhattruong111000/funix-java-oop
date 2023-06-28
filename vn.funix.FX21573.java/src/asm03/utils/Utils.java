package asm03.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String getDivider(){
        return "+----------------------------------------+";
    }

    public static String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String formatBalance(double amount){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(amount);
    }

    public static void main(String[] args) {
        System.out.println(getDivider());
        System.out.printf("%30s%n","BIEN LAI GIAO DICH LOAN");
        System.out.printf("NGAY G/D: %28s%n", getDateTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK: %31s%n", "123456");
        System.out.printf("SO TIEN: %29s%n", formatBalance(1000000));
        System.out.printf("SO DU: %31s%n", formatBalance(100000000));
        System.out.printf("PHI + VAT: %27s%n", formatBalance(10000000*0.01));
        System.out.println(getDivider());

    }
}
