package asm03.models;

import asm02.models.Account;
import asm03.interfaces.ReportService;
import asm03.interfaces.Withdraw;
import asm03.utils.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SavingsAccount extends Account implements Withdraw, ReportService {

    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    private double fee=0;

    public SavingsAccount(){
        super();
    }

    @Override
    public boolean withdraw(double amount) {

        double newBalance =0.0;

        if (isAccepted(amount)){
            newBalance = super.getBalance()- amount;
            super.setBalance(newBalance);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {

        //amount must >=50.000đ
        if (amount< 50000 ){
            System.out.println("So tien rut phai lon hoac bang 50.000đ");
        }else if (amount>SAVINGS_ACCOUNT_MAX_WITHDRAW && super.isPremium()==false){
            System.out.println("So tien rut khong duoc qua 5.000.000đ ");
        } else if (amount> super.getBalance() || (super.getBalance()- amount)<50000) {
            if(amount> super.getBalance()){
                System.out.println("Khong rut duoc tien. Tai khoan khong du tien");

            }
            //message for balance after withdraw must >=50.000đ
            if ((super.getBalance()-amount)<50000 && (super.getBalance()-amount)>=0){
                System.out.println("Rut tien khong hop le. So du con lai neu rut tien thanh cong la: "
                        + NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(super.getBalance()-amount));
                System.out.println("So du con lai sau khi rut phai >= 50.000 đ");
            }
        } else if(amount%10000!=0){
            System.out.println("So tien can rut phai la boi so cua 10.000đ");

        }else {
            System.out.println("Rut tien thanh cong.");
            return true;
        }
        return false;
    }
    public double getFee(){
        return this.fee;
    }

    public String getTitle(){
        return "BIEN LAI GIAO DICH SAVINGS";
    }

    @Override
    public void log(double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf("%30s%n",getTitle());
        System.out.printf("NGAY G/D: %28s%n", Utils.getDateTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", super.getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Utils.formatBalance(amount));
        System.out.printf("SO DU: %31s%n",Utils.formatBalance(super.getBalance()));
        System.out.printf("PHI + VAT: %27s%n", Utils.formatBalance(getFee()));
        System.out.println(Utils.getDivider());

    }
}
