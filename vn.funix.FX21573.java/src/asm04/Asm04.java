package asm04;

import asm04.dao.CustomerDao;
import asm04.exception.CustomerIdNotValidException;
import asm04.model.*;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Asm04 {
    private static final String systemName ="Ngân Hàng Điện Tử";
    private static final String studentId = "fx21573";

    private static  final int EXIT_COMMAND_CODE=0;
    private static  final int EXIT_ERROR_CODE=-1;
    private static  final Scanner scanner = new Scanner(System.in);
    private static  final DigitalBank activeBank =new DigitalBank();
    private static final String CUSTOMER_ID ="001215000001";
    private static final String CUSTOMER_NAME = "FUNIX";

    private static final String systemVersion = "@4.0.0";
    public static void main(String[] args) {
//        Path filePath = Paths.get(".");
//        System.out.println(filePath.toAbsolutePath());
        programRunning();
    }
    public static void menu(){
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| "+systemName.toUpperCase()+" | "
                       +studentId.toUpperCase()+systemVersion+"             |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| 1. Xem danh sách khách hàng                   |");
        System.out.println("| 2. Nhập danh sách khách hàng                  |");
        System.out.println("| 3. Thêm tài khoản ATM                         |");
        System.out.println("| 4. Chuyển tiền                                |");
        System.out.println("| 5. Rút tiền                                   |");
        System.out.println("| 6. Nạp tiền vào tài khoản ATM                 |");
        System.out.println("| 7. Tra cứu lịch sử giao dịch                  |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.print("Chức năng: ");
    }

    public static Customer getCustomerById(){// get customer by CCCD
       try {
           System.out.println("Nhập mã số của khách hàng:");
           String customerId =scanner.next();
           if (activeBank.isValidCustomerId(customerId)){

               return  activeBank.getCustomerById(CustomerDao.list(),customerId);

           }else throw new CustomerIdNotValidException("Mã khách hàng: "+customerId+" không hợp lệ.");
       } catch (CustomerIdNotValidException e){
           System.out.println(e.getMessage());
       }
        return null;
    }

    public static void  addNewCustomerList(){
        System.out.println("+---------------------------------------+");
        System.out.println("Nhập đường dẫn đến tệp: ");
        String dir = scanner.next();
        Path path = Paths.get(dir);

        //add customer list in file customers.txt to the bank
        activeBank.addCustomers(dir);

        System.out.print("Chức năng: ");
    }

    public static void programRunning(){

        //activeBank.addCustomers("store/customers.txt");
        menu();

        while (scanner.hasNext()){
            String optinal =scanner.next();
            if (optinal.equals(String.valueOf(EXIT_COMMAND_CODE))){
                System.out.println("Chương trình đã kết. Cảm ơn bạn đã sử dụng!");
                break;

            } else if (optinal.equals("1")){//show all customers in the bank

                activeBank.showCustomers();
                System.out.print("Chức năng: ");

            } else if (optinal.equals("2")) {// Input the list of customer from file .txt
                addNewCustomerList();
            } else if (optinal.equals("3")) {
                // find customer by id
                Customer customer= getCustomerById();

                if (customer!= null){
                    activeBank.addSavingAccount(scanner, customer.getCustomerId() );

                }else System.out.println("Mã khách hàng không tồn tại");

                System.out.print("Chức năng: ");

            } else if (optinal.equals("4")) {
                // find customer by id
                Customer  customer = getCustomerById();
                if (customer!= null){
                    activeBank.tranfers(scanner,customer.getCustomerId());

                }else System.out.println("Mã khách hàng không tồn tại");

                System.out.print("Chức năng: ");

            } else if (optinal.equals("5")) {

                // find customer by id
                Customer  customer = getCustomerById();
                if (customer!= null){

                    activeBank.withdraw(scanner, customer.getCustomerId());

                }else System.out.println("Mã khách hàng không tồn tại");

                System.out.print("Chức năng: ");

            }else if (optinal.equals("6")){
                // find customer by id
                Customer  customer = getCustomerById();
                if (customer!= null){
                    activeBank.deposit(scanner, customer.getCustomerId());
                }else System.out.println("Mã khách hàng không tồn tại");

                System.out.print("Chức năng: ");


            }else if (optinal.equals("7")){

                DigitalCustomer customer =(DigitalCustomer) getCustomerById();

                if (customer!=null){
                    customer.displayInformation();
                    for (Account account: customer.getAccounts()){
                       account.displayTransactionsList();
                    }

                }else System.out.println("Mã khách hàng không tồn tại");

                System.out.print("Chức năng: ");

            }else {
                System.out.println("Chuc nang khong hop le. Vui long nhap lai.");
                System.out.print("Chuc nang: ");
            }

        }
    }
}
