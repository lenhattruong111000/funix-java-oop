package asm02;

import asm01.Asm01;
import asm02.models.Account;
import asm02.models.Bank;
import asm02.models.Customer;

import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Asm02 {
    private static  final Bank bank = new Bank();
    private static  final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        processRunning();




    }

    public static void menu(){
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| NGAN HANG SO | FX21573@v2.0.0                 |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| 1. Them khach hang                            |");
        System.out.println("| 2. Them tai khoang cho khach hang             |");
        System.out.println("| 3. Hien thi danh sach khach hang              |");
        System.out.println("| 4. Tim theo CCCD                              |");
        System.out.println("| 5. Tim theo ten khach hang                    |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.print("Chuc nang: ");
    }

    public  static void processRunning(){
        menu();
        while (scanner.hasNext()){

            String option =scanner.next();
            Customer customer;
            Account account;

            if (option.equals("0")){
                //menu();
                break;
            }else if (option.equals("1")){
                // add new customer
                customer = new Customer();
                System.out.println("+--------------------------------------------+");

                System.out.print("Nhap ten khach hang: ");
                String customerName = scanner.nextLine();

                System.out.print("Nhap so CCCD: ");
                String customerId =scanner.next();

                //check customerId: valid?, existed?
                while (!Asm01.isValidIdNum(customerId) || bank.isCustomerExisted(customerId)){
                    if (!Asm01.isValidIdNum(customerId))
                        System.out.println("So CCCD khong hop le,");
                    if(bank.isCustomerExisted(customerId))
                        System.out.println("So CCCD da ton tai,");
                    System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

                    //input customer id again
                    customerId = scanner.next();

                    //exit
                    if (customerId.equals("No")){
                        menu();
                        break;
                    }

                }

                if (Asm01.isValidIdNum(customerId) &&
                        !bank.isCustomerExisted(customerId)){
                    customer.setName(customerName);
                    customer.setCustomerId(customerId);
                    bank.addCustomer(customer);
                    System.out.println("da them khach hang "+ customer.getCustomerId()+ " vao danh sach");
                    menu();
                }


            }else if (option.equals("2")){
                //001200123456
                account =new Account();

                System.out.println("Nhap CCCD khach hang:");
                String customerId= scanner.next();

                //check customerId: valid?, notExisted?
                while (!Asm01.isValidIdNum(customerId) || !bank.isCustomerExisted(customerId)){
                    if (!Asm01.isValidIdNum(customerId))
                        System.out.println("So CCCD khong hop le,");
                    else if(!bank.isCustomerExisted(customerId))
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

                if (Asm01.isValidIdNum(customerId) &&
                        bank.isCustomerExisted(customerId)){
                    System.out.println("Nhap ma STK gom 6 chu so:");
                    String accountNumber =scanner.next();
                    //check accountNum: valid?, isExisted?
                    //
                    while (!account.isValidAccountNumber(accountNumber) || bank.isAccountExisted(accountNumber)){
                        if (!account.isValidAccountNumber(accountNumber))
                            System.out.println("So tai khoan khong hop le,");
                        else if(bank.isAccountExisted(accountNumber))
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
                        System.out.println("So du nhap vao không hop le,");
                        System.out.println("So du phai >=50000đ và chỉ chua toan chu so.");
                        System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

                        balance = scanner.next();

                        //exit
                        if (balance.equals("No")){
                            menu();
                            break;
                        }

                    }

                    if(account.isValidAccountNumber(accountNumber) &&
                            !bank.isAccountExisted(accountNumber)){
                        account.setAccountNumber(accountNumber);
                        account.setBalance(Double.parseDouble(balance));
                        bank.addAccount(customerId, account);
                        System.out.println(account.getAccountNumber()+" vua nap "+
                                account.getBalance()+"đ vao tai khoan");
                        menu();

                    }
                }

            }else if (option.equals("3")){
                // testing data
//                Customer customer1 =new Customer();
//                customer1.setName("Funix Education");
//                customer1.setCustomerId("001200123456");
//
//                Account account1 = new Account();
//                account1.setAccountNumber("123456");
//                account1.setBalance(500000);
//
//                Account account2 = new Account();
//                account2.setAccountNumber("123457");
//                account2.setBalance(1000000);
//
//                Account account3 = new Account();
//                account3.setAccountNumber("123458");
//                account3.setBalance(100000);
//
////                customer1.addAccount(account1);
////                customer1.addAccount(account2);
////                customer1.addAccount(account3);
//                bank.addCustomer(customer1);
//                bank.addAccount("001200123456",account1);
//                bank.addAccount("001200123456",account2);
//                bank.addAccount("001200123456",account3);
//
//                Customer customer2 = new Customer();
//                customer2.setName("Le Nhat Truong");
//                customer2.setCustomerId("001400123457");
//
//                Account account4 = new Account();
//                account4.setAccountNumber("123459");
//                account4.setBalance(50000000);
//
//                Account account5 = new Account();
//                account5.setAccountNumber("123450");
//                account5.setBalance(100000000);
//
//                Customer customer3 = new Customer();
//                customer3.setName("FUNIX University");
//                customer3.setCustomerId("001200123455");
//
//                Account account6 = new Account();
//                account6.setAccountNumber("121200");
//                account6.setBalance(5000000);
//
//                Customer customer4 = new Customer();
//                customer4.setName("Nguyen Van Truong");
//                customer4.setCustomerId("001400654321");
//
//                Account account7 = new Account();
//                account7.setAccountNumber("131300");
//                account7.setBalance(9000000);
//
//                Customer customer5 =new Customer();
//                customer5.setName("Nguyen Thi Hue");
//                customer5.setCustomerId("001200121212");
//
//                Account account8 = new Account();
//                account8.setAccountNumber("656534");
//                account8.setBalance(5000000);
//
//                customer2.addAccount(account4);
//                customer2.addAccount(account5);
//                customer3.addAccount(account6);
//                customer4.addAccount(account7);
//                customer5.addAccount(account8);
//
//
//                bank.addCustomer(customer2);
//                bank.addCustomer(customer3);
//                bank.addCustomer(customer4);
//                bank.addCustomer(customer5);

                //
                for ( Customer c: bank.getCustomers()) {
                    c.displayInformation();
                }

//                System.out.print("Nhap No de thoat:");
//                option =scanner.next();
//
//                if (option.equals("No")){
//                    menu();
//                }
                do {
                    System.out.print("Nhap No de thoat:");
                    option =scanner.next();
                    if (option.equals("No")){
                        menu();
                    }
                } while (!option.equals("No"));



            }else if (option.equals("4")){
                System.out.println("Moi nhap CCCD cua khach hang:");
                String customerId = scanner.next();

                //check valid , existed;
                while (!Asm01.isValidIdNum(customerId) || !bank.isCustomerExisted(customerId)){
                    if (!Asm01.isValidIdNum(customerId))
                        System.out.println("So CCCD khong hop le,");
                    else if(!bank.isCustomerExisted(customerId))
                        System.out.println("So CCCD khong ton tai,");
                    System.out.println("Vui long nhap lai, hoac nhap 'No' de thoat ");

                    //input customer id again
                    customerId = scanner.next();

                    //exit
                    if (customerId.equals("No")){
                        menu();
                        break;
                    }

                }

                if (Asm01.isValidIdNum(customerId) && bank.isCustomerExisted(customerId)){
                    Customer customer1 = bank.findCustomer(customerId);
                    customer1.displayInformation();
                    menu();

                }

            }else if (option.equals("5")){
                System.out.println("Moi nhap ten khach hang:");
                String customerName =scanner.next();

                List<Customer>  customerList= bank.searchCustomerByName(customerName);
                if (customerList != null){
                    for (Customer customer1 : customerList){
                        customer1.displayInformation();
                    }
                }else System.out.println("khong tim thay khach hang.");

//                System.out.print("Nhap No de thoat:");
//                option =scanner.next();

                do {
                    System.out.print("Nhap No de thoat:");
                    option =scanner.next();
                    if (option.equals("No")){
                        menu();
                    }
                } while (!option.equals("No"));

            }
        }
    }
}