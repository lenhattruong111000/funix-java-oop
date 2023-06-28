package asm04.model;

import asm03.utils.Utils;
import asm04.dao.AccountDao;
import asm04.dao.CustomerDao;
import asm04.service.TextFileService;;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DigitalBank extends Bank {

    public Customer getCustomerById(String customerId){// get customer by CCCD
        if (isValidCustomerId(customerId)){
            return getCustomerById(CustomerDao.list(),customerId);
        }else System.out.println("Mã khách hàng: "+customerId+" không hợp lệ.");
        return null;
    }

    public List<Transaction> getHistoryTransaction(String customerId, String accountNumber){
        //get customer from the thhe bank
        DigitalCustomer customer = (DigitalCustomer) findCustomer(customerId);
        if (customer!=null){
            //get account form the customer
            Account account = customer.findAccount(accountNumber);
            if (account!=null){
                if (account.getClass().getSimpleName().equals("SavingsAccount")){

                    SavingsAccount savingsAccount = (SavingsAccount) account;

                    return savingsAccount.getTransactions();

                } else if (account.getClass().getSimpleName().equals("LoanAccount")){

                    LoanAccount loanAccount = (LoanAccount) account;

                    return loanAccount.getTransactions();

                }
            }

        }

        return null;
    }

    public boolean withdraw(String customerId, String accountNumber, double amount){
        //get customer from the bank
        DigitalCustomer customer = (DigitalCustomer) findCustomer(customerId);
        if (customer!= null){
            //get account form the customer
            Account account = customer.findAccount(accountNumber);
            if (account!= null){
                if (account.getClass().getSimpleName().equals("SavingsAccount")){

                    //Create transaction
                    Transaction transaction =new Transaction();

                    //down-casting
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    boolean status = savingsAccount.withdraw(amount);

                    transaction.setAmount(amount);
                    transaction.setAccountNumber(accountNumber);
                    transaction.setTime(Utils.getDateTime());
                    transaction.setStatus(status);

                    //store history of transaction
                    savingsAccount.getTransactions().add(transaction);

                    //print bill
                    savingsAccount.log(amount);
                    return true;

                } else if (account.getClass().getSimpleName().equals("LoanAccount")){
                    //create transaction
                    Transaction transaction =new Transaction();

                    //down-casting
                    LoanAccount loanAccount = (LoanAccount) account;
                    boolean status = loanAccount.withdraw(amount);

                    //store history of transaction

                    transaction.setAmount(amount);
                    transaction.setAccountNumber(accountNumber);
                    transaction.setTime(Utils.getDateTime());
                    transaction.setStatus(status);
                    loanAccount.getTransactions().add(transaction);

                    //print bill
                    loanAccount.log(amount);

                    return true;
                }

            }
        }
        return false;
    }

    public void showCustomers(){
       List<Customer> customers = CustomerDao.list();
       //customers.forEach(System.out::println);
       if (!customers.isEmpty()){
           for (Customer customer: customers) {
               DigitalCustomer digitalCustomer = (DigitalCustomer) customer;
               digitalCustomer.displayInformation();
           }
       }else {
           System.out.println("Chưa có khách hàng nào trong danh sách!");
       }
    }

    public Customer getCustomerById(List<Customer> customers,String customerId) {
        for (Customer customer: customers) {
            if (customer.getCustomerId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }

    public boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber.matches("^[0-9]{6}$")){
            return true;
        }else {
            System.out.println("Số tài khoản mới không hợp lệ.");
        }
        return false;

    }


    public boolean isAccountExisted(List<Account> accountsList, Account newAccount){
        List<String> accountIdList = accountsList.stream().map(e -> e.getAccountNumber()).collect(Collectors.toList());

        if (accountIdList.contains(newAccount.getAccountNumber())){
            System.out.println("Tài khoản đã tồn tại.");
            return true;
        }
        return false;
    }

    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer){
        if (customers.contains(newCustomer)){
            return true;
        }
        return false;
    }

    public void withdraw(Scanner scanner, String customerId){
        // find customer by id
        Customer  customer = getCustomerById(customerId);

        //display information about customer account
        if (!customer.getAccounts().isEmpty()){
            customer.displayInformation();

            //call withdraw method of customer.
            customer.withdraw(scanner);

        }else System.out.println("Khách hàng chưa có tài khoản.");

    }

    public void tranfers(Scanner scanner, String customerId){
        // find customer by id
        Customer  customer = getCustomerById(customerId);

        //display information about customer account
        if (!customer.getAccounts().isEmpty()){
            customer.displayInformation();

            //call tranfer meethod of customer.
            customer.transfers(scanner);

        }else System.out.println("Khách hàng chưa có tài khoản.");

    }

    public void deposit(Scanner scanner, String customerId){
        // find customer by id
        Customer  customer = getCustomerById(customerId);

        //display information about customer account
        if (!customer.getAccounts().isEmpty()){
            customer.displayInformation();

            //call deposit method of customer.
            customer.deposit(scanner);

        }else System.out.println("Khách hàng chưa có tài khoản.");

    }

    public boolean checkingAccountNumberInput(String accountNumber , Account newAccount){

        if(isValidAccountNumber(accountNumber)
                && !isAccountExisted(AccountDao.list(), newAccount)){//check the accountNumber input is valid or not
            return true;
        }
        return false;
    }

    public boolean checkingInitialBalance(String initialBalance){

        if(initialBalance.matches("^[0-9]*$")
                && Double.parseDouble(initialBalance)>=50000
                && Double.parseDouble(initialBalance)%10000==0){
            return true;
        } else if (!initialBalance.matches("^[0-9]*$")){
            System.out.println("số dư nhập vào không hợp lệ");
        } else if (Double.parseDouble(initialBalance)<50000){
            System.out.println("Sô dư nhập vào phải >= 50.000đ");
        } else if (Double.parseDouble(initialBalance)%10000!=0){
            System.out.println("Số dư nhập vào phải là bội của 10.000đ");
        }
        return false;
    }

    public boolean addSavingAccount(Scanner scanner, String customerId) {
        List<Account> currentAccounts = AccountDao.list();
        scanner = new Scanner(System.in);
        String accountNumber= null;
            if (getCustomerById(CustomerDao.list(),customerId)!=null){
                SavingsAccount account = new SavingsAccount();

                // get new accountNumber
                System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
                accountNumber = scanner.next();
                account.setAccountNumber(accountNumber);

                while (!checkingAccountNumberInput(accountNumber, account)) {
                    System.out.print("Nhập số tài khoản gồm 6 chữ số: ");
                    accountNumber =scanner.next();
                    account.setAccountNumber(accountNumber);
                }

                //get initial balance for new account
                System.out.print("Nhập số dư tài khoan >=50000: ");
                String initialBalance = scanner.next();

                //check initial balance input
                while (!checkingInitialBalance(initialBalance)){
                    System.out.print("Nhập số dư tài khoan >=50000: ");
                    initialBalance = scanner.next();
                }

                //set initial value for account
                account.setCustomerId(customerId);
                account.setAccountNumber(accountNumber);
                account.setBalance(Double.parseDouble(initialBalance));

                //update customer (update account list of customer)
                //Customer customer = getCustomerById(CustomerDao.list(), customerId);
                //customer.addAccount(account);
                //CustomerDao.update(customer);

                //add new account to current account list
                currentAccounts.add(account);
                // store update account to file accounts.dat
                AccountDao.save(currentAccounts);

                //create a transaction and store to file
                account.createTransaction(Double.parseDouble(initialBalance), Utils.getDateTime(),true, TransactionType.DEPOSIT );
                System.out.println("Tạo tài khoản thành công.");
                System.out.println("Hoàn thành giao dịch đầu tiên. Hóa đơn giao dịch:");
                account.log(Double.parseDouble(initialBalance));


                return true;
            }
        return false;
    }

    public boolean addCustomers(String fileName) {
        //get customer list from the .txt file
        List<List<String>> newCustomerInfoList = TextFileService.readFile(fileName);

        //get current customer from the bank store in customers.dat file
        List <Customer> currentCustomers = CustomerDao.list();
        Customer customer;

        //create a list store all customer id of current customer in bank
        List<String> currentCustomerIds = currentCustomers.stream().map(e->e.getCustomerId()).collect(Collectors.toList());
        //currentCustomerIds.forEach(System.out::println);

        if(!newCustomerInfoList.isEmpty()){
            for (int i = 0; i < newCustomerInfoList.size(); i++) {

                customer = new DigitalCustomer();

                // if the customer id is not valid: the result of getCustomerId()= null
                customer.setCustomerId(newCustomerInfoList.get(i).get(0));
                customer.setName(newCustomerInfoList.get(i).get(1));

                if(!currentCustomerIds.contains(customer.getCustomerId()) && customer.getCustomerId()!=null){

                    currentCustomers.add(customer);
                    super.addCustomer(customer);

                    System.out.println("Đã thêm khách hàng "+customer.getCustomerId()+" vào danh sách khách hàng");
                } else if(customer.getCustomerId()!= null) {
                    System.out.println("Khách hàng "+ customer.getCustomerId()+" đã tồn tại, thêm khách hàng không thành công.");
                }
            }
            CustomerDao.save(currentCustomers);
            return true;
        }
        return false;
    }

}
