package asm04.dao;

import asm04.model.Account;
import asm04.service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public static final String FILE_PATH = "store/accounts.dat";

    public static void save(List<Account> accounts){
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Account editAccount){
        //get account list from the file
        List<Account> accounts = list();

        //using stream check the existed of account input: editAccount
        boolean hasExist = accounts.stream()
                .anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        //create a list of updated account
        List<Account> updatedAccounts;
        if(!hasExist){ // if the account is not existed in the file: add as new account
            // initial element values in updatedList is the same as list customer in the file
            updatedAccounts = new ArrayList<>(accounts);
            updatedAccounts.add(editAccount);
        } else { // if customer is existed in the file
            //create new array list to store the existed customer that wants to update
            updatedAccounts = new ArrayList<>();

            for (Account account: accounts) {
                // the main idea is:
                // return the updateList that is not change the value of (current account list =accounts)
                // solution:    - create a new arrayList
                //              - using for loop to check the input account has existed in current account list or not
                //              - if it does not exist: copy all the account in current account list to updatedAccounts
                //              - if it exists: replace the current checking account by (input account = editAccount)

                if(account.getAccountNumber().equals((editAccount.getAccountNumber()))){
                    updatedAccounts.add(editAccount);
                } else {
                    updatedAccounts.add(account);
                }
            }
        }
        save(updatedAccounts);
    }
}
