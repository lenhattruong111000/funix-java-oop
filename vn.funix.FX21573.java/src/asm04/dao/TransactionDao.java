package asm04.dao;

import asm04.model.Transaction;
import asm04.service.BinaryFileService;

import java.util.List;

public class TransactionDao {
    public static final String FILE_PATH = "store/transactions.dat";

    public static void save(List<Transaction> transactions){
        BinaryFileService.writeFile(FILE_PATH, transactions);
    }

    public static List<Transaction> list(){
        return BinaryFileService.readFile(FILE_PATH);
    }
}
