package asm04.model;

import asm03.utils.Utils;
import asm04.Asm04;
import asm04.dao.AccountDao;
import asm04.dao.CustomerDao;
import asm04.dao.TransactionDao;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {

    private static final long serialVersionUID =1L;

    private List<Account> accounts;

    private SavingsAccount transferAccount;
    private SavingsAccount receiveAccount;

    private SavingsAccount withdrawAccount;

    private SavingsAccount depositAccount;

    private double amount;

    public Customer(){
        accounts =new ArrayList<Account>();
    }

    public List<Account> getAccounts() {
        this.accounts = AccountDao.list().stream()
                .filter(e -> e.getCustomerId().equals(super.getCustomerId()))
                .collect(Collectors.toList());
        return accounts;
    }

    public boolean isPremium(){
        for (Account account: getAccounts()) {
            if (account.isPremium())
                return true;
        }
        return false;
    }

    // find account by AccountNumer
    public Account findAccount(String accountNumber){
        if (getAccounts()!=null){
            for ( Account account: getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber))
                    return account;
            }
        }
        return null;
    }

    public boolean addAccount(Account account){
        if (findAccount(account.getAccountNumber())==null){
            getAccounts().add(account);
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
        for (Account account: getAccounts()) {
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
    public boolean isValidAccount(List<Account> accounts ,String accountNumber){ //vaild account: have 6 numbers && exist
        if (accounts.stream()
                .map(e -> e.getAccountNumber())
                .collect(Collectors.toList())
                .contains(accountNumber)
                && accountNumber.matches("^[0-9]{6}$")){
            return true;
        } else if(!accountNumber.matches("^[0-9]{6}$")) System.out.println("Tài khoản không hợp lệ");
        else System.out.println("Tài khoản không tồn tại hoặc không thuộc quyền sở hữu của khách hàng.");
        return false;
    }

    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber){
        for (Account account: accounts) {
            if (account.getAccountNumber().equals(accountNumber)){
                return account;
            }
        }
        return null;
    }

    public Customer getCustomerById(String customerId){
        return CustomerDao.list()
                .stream()
                .filter(customer1 -> customer1.getCustomerId().equals(customerId))
                .collect(Collectors.toList()).get(0);

    }

    public String getTransferAccountNumber(Scanner scanner){
        scanner = new Scanner(System.in);
        String transferAccountNumber=null;
        do {
            System.out.print("Nhập tài khoản chuyển tiền: ");
            transferAccountNumber = scanner.next();

        } while (!isValidAccount(getAccounts(), transferAccountNumber));
        return transferAccountNumber;
    }

    public String getReceiveAccountNumber(Scanner scanner, String transferAccountNumber){
        scanner = new Scanner(System.in);
        String receiveAccountNumber = "";
        do{
            if (receiveAccountNumber.equals(transferAccountNumber)){
                System.out.println("số tài khoản nhận phải khác số tài khoản chuyển tiền.");
            }

            System.out.print("Nhập số tài khoản nhận (exit để thoát): ");
            receiveAccountNumber =scanner.next();

            //exit condition
            if (receiveAccountNumber.equals("exit")){
                break;
            }
        }while (!isValidAccount(AccountDao.list(),receiveAccountNumber)
                || receiveAccountNumber.equals(transferAccountNumber));

        return receiveAccountNumber;
    }

    public void printReceiveAccountInfo(String receiveAccountNumber){
        String receiveCustomerName = getCustomerById(
                getAccountByAccountNumber(AccountDao.list(),receiveAccountNumber).getCustomerId()).getName();

        System.out.println("Gửi tiền đến tài khoản: "
                + receiveAccountNumber
                + " | "
                + receiveCustomerName);
    }

    public boolean isValidAmountInput(Account account,String amount){

        if (amount.matches("^[0-9]*$") // check: amount input is a number or not
                && Double.parseDouble(amount) <=account.getBalance()-50000 // the balance must >= 50.000đ --> amount <= balance - 5000đ
                && Double.parseDouble(amount)>= 50000 // the minimum amount in 1 transaction >=50.000đ
                && Double.parseDouble(amount)% 10000==0){ //Amount must be a multiple of 10.000đ

            if(!account.isPremium()){
                if (Double.parseDouble(amount)>5000000){// With Normal account: 1 transaction <= 5.000.000đ
                    System.out.println("Giao dịch của tài khoản Normal không quá 5.000.000đ/giao dịch.");
                    return false;
                }
            }
            return true;
        } else if (!amount.matches("^[0-9]*$")){
            System.out.println("Số tiền nhập vào không hợp lệ.");
        }else if (Double.parseDouble(amount) >account.getBalance()-50000){
            System.out.println("số tiền nhập vào phải <= "
                    + NumberFormat
                    .getCurrencyInstance(new Locale("vi","VN"))
                    .format(account.getBalance()-50000));
        }else if(Double.parseDouble(amount)<50000){
            System.out.println("Số tiền nhập vào phải >= 50.000đ.");
        }else  if (Double.parseDouble(amount)% 10000 !=0){
            System.out.println("Số tiền nhập vào phải là bội số của 10.000đ");
        } else if (Double.parseDouble(amount)>5000000
                && !account.isPremium()){
            System.out.println("Giao dịch của tài khoản Normal không quá 5.000.000đ/giao dịch.");

        }

        return false;
    }

    public String getAmountInput(Scanner scanner, String transferAccountNumber){
        scanner = new Scanner(System.in);
        String amount ="0";
        do {
            System.out.print("Nhập số tiền: ");
            amount = scanner.next();
        }
        while(!isValidAmountInput(
                getAccountByAccountNumber(AccountDao.list(), transferAccountNumber),
                amount));

        return amount;
    }
    public boolean isValidAcceptedStatusInput(String acceptedStatus){
        if (acceptedStatus.toUpperCase().matches("^[Y|N]$")){// accept 1 value input: Y or N
            return true;
        }
        System.out.println("Kí tự nhập vào không hợp lệ.");
        return false;
    }

    public boolean verifyTransferTransaction(Scanner scanner,String transferAccountNumber, String receiveAccountNumber,String amount){
        scanner = new Scanner(System.in);
        String isAccepted ="";
        do {
            System.out.print("Xác nhận thực hiện chuyển "
                    + NumberFormat
                    .getCurrencyInstance(new Locale("vi","VN"))
                    .format(Double.parseDouble(amount))
                    + " từ tài khoản ["
                    + transferAccountNumber
                    + "] đến tài khoản ["
                    + receiveAccountNumber
                    + "] (Y/N): " );
            isAccepted = scanner.next(); // accept value: Y or N
        } while (!isValidAcceptedStatusInput(isAccepted));

        if (isAccepted.toUpperCase().equals("Y")){
            return true;
        } else if (isAccepted.toUpperCase().equals("N")){
            return false;
        }
        return false;
    }

    public boolean transfers(Scanner scanner){
        scanner = new Scanner(System.in);

        //input account number to transfer money
        String transferAccountNumber= getTransferAccountNumber(scanner);

        //input account number to receive money
        String receiveAccountNumber = getReceiveAccountNumber(scanner, transferAccountNumber);
        if (receiveAccountNumber.equals("exit")){
            return false;
        }

        // print information of customer receive money
        printReceiveAccountInfo(receiveAccountNumber);

        //input the amount of money that needs transfer.
        String amount=getAmountInput(scanner, transferAccountNumber);

        //verify the transaction.
        boolean isAccepted= verifyTransferTransaction(scanner, transferAccountNumber, receiveAccountNumber, amount);

        //call transfers method of Account to transfer money
        if (isAccepted){
            this.amount = Double.parseDouble(amount);
            transferAccount= (SavingsAccount) findAccount(transferAccountNumber);
            receiveAccount =(SavingsAccount) getAccountByAccountNumber(AccountDao.list(),receiveAccountNumber);

            //transfer money
            transferAccount.transfer(receiveAccount, this.amount);
            transferAccount.setBalance(transferAccount.getBalance()-this.amount);

            //store new balance to the file
            AccountDao.update(transferAccount);


            //display the current transaction
            System.out.println("Chuyển tiền thành công, biên lai giao dịch: ");
            displayTransactionInformation();
            //return true;
        }else {
            System.out.println("Đã hủy giao dịch.");
            return false;
        }

        //create and store transaction
        transferAccount.createTransaction(this.amount*(-1),Utils.getDateTime(),isAccepted,TransactionType.TRANSFERS);
        receiveAccount.createTransaction(this.amount,Utils.getDateTime(),isAccepted,TransactionType.TRANSFERS);
        return true;
    }

    public String getWithdrawAccountNumber(Scanner scanner){
        scanner = new Scanner(System.in);
        String withdrawAccountNumber = "";
        do{

            System.out.print("Nhập số tài khoản cần rút (exit để thoát): ");
            withdrawAccountNumber =scanner.next();

            //exit condition
            if (withdrawAccountNumber.equals("exit")){
                break;
            }
        }while (!isValidAccount(AccountDao.list(),withdrawAccountNumber));

        return withdrawAccountNumber;
    }

    public void withdraw(Scanner scanner) {
        scanner = new Scanner(System.in);

        //input account number to withdraw money
        String withdrawAccountNumber= getWithdrawAccountNumber(scanner);

        //input the amount of money that needs withdraw.
        String amount = getAmountInput(scanner, withdrawAccountNumber);

        this.amount = Double.parseDouble(amount);
        withdrawAccount= (SavingsAccount) findAccount(withdrawAccountNumber);

        //withdraw money
        boolean status = withdrawAccount.withdraw(this.amount);

        //update and store new balance to the file
        AccountDao.update(withdrawAccount);


        //display the current transaction
        System.out.println("Rút tiền thành công, biên lai giao dịch: ");
        withdrawAccount.log(this.amount);
        //return true;


        //create and store transaction
        withdrawAccount.createTransaction(this.amount*(-1),Utils.getDateTime(),status,TransactionType.WITHDRAW);

    }

    private String getDepositAccountNumber(Scanner scanner) {
        scanner = new Scanner(System.in);
        String depositAccountNumber=null;
        do {
            System.out.print("Nhập tài khoản cần nạp: ");
            depositAccountNumber = scanner.next();

        } while (!isValidAccount(getAccounts(), depositAccountNumber));
        return depositAccountNumber;
    }

    public boolean isValidDepositAmountInput(Account account,String amount){

        if (amount.matches("^[0-9]*$") // check: amount input is a number or not
                && Double.parseDouble(amount)>= 50000 // the minimum amount in 1 deposit transaction >=50.000đ
                && Double.parseDouble(amount)% 10000==0){ //Amount must be a multiple of 10.000đ

            return true;
        } else if (!amount.matches("^[0-9]*$")){
            System.out.println("Số tiền nhập vào không hợp lệ.");
        }else if(Double.parseDouble(amount)<50000){
            System.out.println("Số tiền nhập vào phải >= 50.000đ.");
        }else  if (Double.parseDouble(amount)% 10000 !=0){
            System.out.println("Số tiền nhập vào phải là bội số của 10.000đ");
        }

        return false;
    }
    public String getDepositAmountInput(Scanner scanner, String transferAccountNumber){
        scanner = new Scanner(System.in);
        String amount ="0";
        do {
            System.out.print("Nhập số tiền nạp: ");
            amount = scanner.next();
        }
        while(!isValidDepositAmountInput(
                getAccountByAccountNumber(AccountDao.list(), transferAccountNumber),
                amount));

        return amount;
    }

    public void deposit(Scanner scanner) {

        scanner = new Scanner(System.in);

        //input account number to deposit
        String depositAccountNumber= getDepositAccountNumber(scanner);

        //input the amount of money that needs to deposit.
        String amount = getDepositAmountInput(scanner, depositAccountNumber);

        this.amount = Double.parseDouble(amount);
        depositAccount= (SavingsAccount) findAccount(depositAccountNumber);

        //withdraw money
        boolean status = depositAccount.deposit(this.amount);

        //update and store new balance to the file
        AccountDao.update(depositAccount);


        //display the current transaction
        System.out.println("Nạp tiền thành công, biên lai giao dịch: ");
        depositAccount.log(this.amount);
        //return true;


        //create and store transaction
        depositAccount.createTransaction(this.amount,Utils.getDateTime(),status,TransactionType.DEPOSIT);

    }



    public void  displayTransactionInformation(){
        System.out.println(Utils.getDivider());
        System.out.printf("| %30s         |%n",transferAccount.getTitle());
        System.out.printf("| NGÀY GD: %29s |%n", Utils.getDateTime());
        System.out.printf("| ATM ID: %30s |%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("| SỐ TK: %31s |%n", transferAccount.getAccountNumber());
        System.out.printf("| SỐ TK NHẬN: %26s |%n", receiveAccount.getAccountNumber());
        System.out.printf("| SỐ TIỀN CHUYỂN: %22s |%n", Utils.formatBalance(amount));
        System.out.printf("| SỐ DƯ TK: %28s |%n",Utils.formatBalance(transferAccount.getBalance()));
        System.out.printf("| PHÍ + VAT: %27s |%n", Utils.formatBalance(transferAccount.getFee()));
        System.out.println(Utils.getDivider());
    }

}
