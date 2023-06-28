package asm04.model;

import asm03.interfaces.ReportService;
import asm03.interfaces.Withdraw;
import asm03.utils.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoanAccount extends Account implements Withdraw, ReportService {
    private  static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private  static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;

    private  static  final  double LOAN_ACCOUNT_MAX_BALANCE = 100000000;
    private  double fee =0.0;

    public LoanAccount(){

        super();
    }

    @Override
    public boolean isValidBalance(double balance){
        if (balance<=LOAN_ACCOUNT_MAX_BALANCE && balance>=50000){
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidBalance(String balance){
        String regex ="^[0-9]+$";
        if (Double.parseDouble(balance)<=LOAN_ACCOUNT_MAX_BALANCE
                && Double.parseDouble(balance)>=50000
                && balance.matches(regex)){
            return true;
        }
        return false;
    }

    @Override
    public void setBalance(double balance){
        if (isValidBalance(balance)){
            super.setBalance(balance);
        }else System.out.println("so du phai be hon 100.000.000đ");
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)){
            super.setBalance(super.getBalance()-amount-getFee(amount));
            return true;
        }
       return false;
    }

    @Override
    public boolean isAccepted(double amount) {

        if (amount> LOAN_ACCOUNT_MAX_BALANCE){
            System.out.println("So tien rut khong vuot qua 100.000.000đ");
        }else if (amount> super.getBalance() || (super.getBalance()- amount-getFee(amount))<50000) {
            if(amount> super.getBalance()){
                System.out.println("Khong rut duoc tien. Tai khoan khong du tien");

            }
            //message for balance after withdraw must >=50.000đ
            if ((super.getBalance()-amount-getFee(amount))<50000 && (super.getBalance()-amount-getFee(amount))>=0){
                System.out.println("Rut tien khong hop le. So du con lai neu rut tien thanh cong la: "
                        + NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(super.getBalance()-amount-getFee(amount)));
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


    public double getFee(double amount){
        if (isPremium()){
            this.fee = amount*LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE;

        }else {
            this.fee = amount*LOAN_ACCOUNT_WITHDRAW_FEE;
        }
        return this.fee;
    }

    public double getFee(){
       return this.fee;
    }



    public String getTitle(){
        return "BIEN LAI GIAO DICH LOAN";
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
