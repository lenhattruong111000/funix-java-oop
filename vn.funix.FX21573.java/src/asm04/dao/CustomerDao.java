package asm04.dao;

import asm04.service.BinaryFileService;
import asm04.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public static final String FILE_PATH = "store/customers.dat";

    public static void save(List<Customer> customers){
        BinaryFileService.writeFile(FILE_PATH, customers);
    }

    public static List<asm04.model.Customer> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Customer editCustomer){
        //get account list from the file
        List<Customer> customers = list();

        //using stream check the existed of account input: editAccount
        boolean hasExist =customers.stream()
                .anyMatch(customer -> customer.getCustomerId().equals(editCustomer.getCustomerId()));
        //create a list of updated account
        List<Customer> updatedCustomers;
        if(!hasExist){ // if the account is not existed in the file: add as new account
            // initial element values in updatedList is the same as list customer in the file
            updatedCustomers = new ArrayList<>(customers);
            updatedCustomers.add(editCustomer);
        } else { // if customer is existed in the file
            //create new array list to store the existed customer that wants to update
            updatedCustomers = new ArrayList<>();

            for (Customer customer: customers) {
                // the main idea is:
                // return the updateList that is not change the value of (current account list =accounts)
                // solution:    - create a new arrayList
                //              - using for loop to check the input account has existed in current account list or not
                //              - if it does not exist: copy all the account in current account list to updatedAccounts
                //              - if it exists: replace the current checking account by (input account = editAccount)

                if(customer.getCustomerId().equals((editCustomer.getCustomerId()))){
                    updatedCustomers.add(editCustomer);
                } else {
                    updatedCustomers.add(customer);
                }
            }
        }
        save(updatedCustomers);
    }
}
