package asm03.models;

import asm01.Asm01;
import asm02.models.Account;
import asm02.models.Customer;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Asm03 {
    private static  final int EXIT_COMMAND_CODE=0;
    private static  final int EXIT_ERROR_CODE=-1;
    private static  final Scanner scanner = new Scanner(System.in);
    private static  final DigitalBank activeBank =new DigitalBank();
    private static final String CUSTOMER_ID ="001215000001";
    private static final String CUSTOMER_NAME = "FUNIX";

    private static Account account;

    public static void main(String[] args) {

        //Create a customer
        DigitalCustomer customer = new DigitalCustomer();
        customer.setCustomerId(CUSTOMER_ID);
        customer.setName(CUSTOMER_NAME);

        double savingsBalanceInit = 77500000;
        double loanBalanceInit =10000000;
        // create account for customer
        Account savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber("123456");
        if(savingsAccount.isValidBalance(savingsBalanceInit)) {
            //savingsAccount.setBalance(77500000);
            savingsAccount.setBalance(savingsBalanceInit);
            //add account for customer
            customer.addAccount(savingsAccount);
        }

        Account loanAccount = new LoanAccount();
        loanAccount.setAccountNumber("234567");
        if (loanAccount.isValidBalance(loanBalanceInit)){
            //add account for customer
            //loanAccount.setBalance(10000000);
            loanAccount.setBalance(loanBalanceInit);
            customer.addAccount(loanAccount);
        }

        //add customer to the bank
        activeBank.addCustomer(customer);

        //show customer information
        //showCustomer();

        //running program
        programRunning();


    }

    public static void menu(){
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| NGAN HANG SO | FX21573@v3.0.0                 |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| 1. Thong tin khach hang                       |");
        System.out.println("| 2. Them tai khoan ATM                         |");
        System.out.println("| 3. Them tai khoan tin dung                    |");
        System.out.println("| 4. Rut tien                                   |");
        System.out.println("| 5. Lich su giao dich                          |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.print("Chuc nang: ");
    }

    public static void showCustomer(){
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
//        if(customer!= null){
//            customer.displayInformation();
//        }
        try{
            customer.displayInformation();
        }catch (NullPointerException e){
            System.out.println("khong co khach hang trong danh sach.");
        }

    }


    public  static  void addAccount(Account account){

        System.out.println("Nhap CCCD khach hang:");
        String customerId= scanner.next();

        //check customerId: valid?, notExisted?
        while (!Asm01.isValidIdNum(customerId) || !activeBank.isCustomerExisted(customerId)){
            // show exception message
            if (!Asm01.isValidIdNum(customerId))
                System.out.println("So CCCD khong hop le,");
            else if(!activeBank.isCustomerExisted(customerId))
                System.out.println("So CCCD khong ton tai trong danh sach,");
            System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

            //input customer id again
            customerId = scanner.next();

            //exit
            if (customerId.equals("No")){
                menu();
                break;
            }
        }
        // handle event: customerId is valid and existed
        if (Asm01.isValidIdNum(customerId) &&
                activeBank.isCustomerExisted(customerId)){
            System.out.println("Nhap ma STK gom 6 chu so:");
            String accountNumber =scanner.next();

            //check accountNumber: valid?, isExisted?
            while (!account.isValidAccountNumber(accountNumber) || activeBank.isAccountExisted(accountNumber)){
                if (!account.isValidAccountNumber(accountNumber))
                    System.out.println("So tai khoan khong hop le,");
                else if(activeBank.isAccountExisted(accountNumber))
                    System.out.println("So tai khoan da ton tai trong danh sach,");
                System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

                //input accountNumber again
                accountNumber =scanner.next();

                //exit
                if (accountNumber.equals("No")){
                    menu();
                    break;
                }

            }
            // input balance
            System.out.println("Nhap so du:");
            String balance = scanner.next();



            //check balance
            while (!account.isValidBalance(balance)){
                //invalid balance message
                System.out.println("So du nhap vao không hop le,");
                if (account.getClass().getSimpleName().equals("SavingsAccount")){
                    System.out.println("So du phai >=50000đ doi voi tai khoan ATM và chỉ chua toan chu so.");

                }else {
                    System.out.println("So du phai >=50000đ va <=100.000.000đ doi voi tai khoan tin dung và chỉ chua toan chu so.");
                }
                System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

                //input balance again
                balance = scanner.next();

                //exit
                if (balance.equals("No")){
                    menu();
                    break;
                }

            }
            // handle event: vaild accountNumber
            if(account.isValidAccountNumber(accountNumber) &&
                    !activeBank.isAccountExisted(accountNumber)){

                account.setAccountNumber(accountNumber);
                account.setBalance(Double.parseDouble(balance));
                activeBank.addAccount(customerId, account);

                System.out.println(account.getAccountNumber()+" vua nap "+
                        NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(account.getBalance())+" vao tai khoan");
                menu();

            }
        }
    }

    // function for withdraw
    public  static void withdraw(){
        //input customer id
        System.out.println("Moi nhap CCCD: ");
        String customerId = scanner.next();

        //============================================check valid customer id=================================//
        while (!isValidCustomerIdOfBank(customerId)){
            System.out.println("CCCD khong hop le. Moi Nhap Lai CCCD");
            System.out.println("hoac nhap 'No' de thoat.");

            customerId = scanner.next();

            if (customerId.equals("No")){
                System.out.print("Chuc nang: ");
                break;
            }


        }
        // customerId is valid -> next step
        if(isValidCustomerIdOfBank(customerId)){
            //input accountNumber
            System.out.println("Moi ban nhap so tai khoan:");
            String accountNumber = scanner.next();

            //=============================================check valid accountNumber==========================//
            while (!isValidAccountNumberOfBank(accountNumber)){
                System.out.println("So tai khaon khong hop le. Moi Nhap Lai:");
                System.out.println("hoac nhap 'No' de thoat.");

                accountNumber = scanner.next();

                if (accountNumber.equals("No")){
                    System.out.print("Chuc nang: ");
                    break;
                }
            }

            //accountNumer is valid -> do next step
            if (isValidAccountNumberOfBank(accountNumber)){

                //amount of money that wants to withdraw
                System.out.println("Moi nhap so tien can rut");
                String amount =scanner.next();

                //==========================================check valid amount================================//
                while (!isValidNumber(amount)){
                    System.out.println("So tien nhap vao khong phai so.");
                    System.out.println("Moi nhap lai so tien can rut.");
                    System.out.println("Hoac nhan 'No' de thoat");

                    amount =scanner.next();

                    if (amount.equals("No")){
                        System.out.print("Chuc nang: ");
                        break;
                    }
                }

                if (isValidCustomerIdOfBank(customerId)
                        && isValidAccountNumberOfBank(accountNumber)
                        && isValidNumber(amount)){
                    try {
                        activeBank.withdraw(customerId, accountNumber, Double.parseDouble(amount));

                    }catch (Exception e){
                        System.out.println("khong the rut tien");
                    }
                    System.out.print("Chuc nang: ");
                }
            }
        }

    }

    public static void getHistoryTransaction(){
        //input customer id
        System.out.println("Moi nhap CCCD: ");
        String customerId = scanner.next();

        //============================================check valid customer id=================================//
        while (!isValidCustomerIdOfBank(customerId)){
            System.out.println("CCCD khong hop le. Moi Nhap Lai CCCD");
            System.out.println("hoac nhap 'No' de thoat.");

            customerId = scanner.next();

            if (customerId.equals("No")){
                System.out.print("Chuc nang: ");
                break;
            }


        }
        // customerId is valid -> next step
        if(isValidCustomerIdOfBank(customerId)){
            //input accountNumber
            System.out.println("Moi ban nhap so tai khoan:");
            String accountNumber = scanner.next();

            //=============================================check valid accountNumber==========================//
            while (!isValidAccountNumberOfBank(accountNumber)){
                System.out.println("So tai khaon khong hop le. Moi Nhap Lai:");
                System.out.println("hoac nhap 'No' de thoat.");

                accountNumber = scanner.next();

                if (accountNumber.equals("No")){
                    System.out.print("Chuc nang: ");
                    break;
                }
            }

            //accountNumer is valid -> do next step
            if (isValidAccountNumberOfBank(accountNumber)){

                List<Transaction> transactions= activeBank.getHistoryTransaction(customerId,accountNumber);
                for (Transaction transaction : transactions){
                    transaction.displayTransaction();
                }

            }
        }
    }

    // valid CCCD and existed in the bank
    public static boolean isValidCustomerIdOfBank(String customerId){
        if (Asm01.isValidIdNum(customerId) && activeBank.isCustomerExisted(customerId)){
          return true;
        }
        return false;
    }

    public static boolean isValidAccountNumberOfBank(String accountNumber){
        try {
            if (accountNumber.matches("[0-9]{6}")
                    && activeBank.isAccountExisted(accountNumber)){
               return true;
            }
        } catch (NullPointerException e){
            System.out.println("Account Number is null");
            return false;
        }
        return false;
    }

    public static  boolean isValidNumber(String amount){
        try{
            if(amount.matches("[0-9]*")){
                return true;
            }
        }catch (NullPointerException e){
            return false;
        }
        return false;
    }


    public static void programRunning(){
        menu();

        while (scanner.hasNext()){
            String optinal =scanner.next();
            if (optinal.equals(String.valueOf(EXIT_COMMAND_CODE))){
                System.out.println("Chuong trinh da ket thuc. Cam on ban da su dung");
                break;

            } else if (optinal.equals("1")){
                showCustomer();
                System.out.print("Chuc nang: ");
            } else if (optinal.equals("2")) {
                account = new SavingsAccount();
                addAccount(account);

            } else if (optinal.equals("3")) {
                account =new LoanAccount();
                addAccount(account);

            } else if (optinal.equals("4")) {
                withdraw();

            } else if (optinal.equals("5")) {
                  getHistoryTransaction();
            } else {
                    System.out.println("Chuc nang khong hop le. Vui long nhap lai.");
                System.out.print("Chuc nang: ");
            }

        }

    }

}
