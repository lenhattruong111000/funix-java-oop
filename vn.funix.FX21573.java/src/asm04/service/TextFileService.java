package asm04.service;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName){
        //define the scanner to read the data from the file
        Scanner scanner = null;
        List<List<String>> customerInfoList = new ArrayList<>();

        try {
            scanner = new Scanner(new FileReader(fileName));

            // data is separated by comma
            scanner.useDelimiter(COMMA_DELIMITER);

            while(scanner.hasNextLine()) { // start reading data

               //create a list store 2 values customerId and customerName
                List<String> customerInfo = new ArrayList<>();

                // add data to the array  that is separated by comma
                String[] info = scanner.nextLine().split(COMMA_DELIMITER);
                //System.out.println("id: "+info[0].toString()+":"+info[1]);

                //add data to the list
                customerInfo.add(info[0]);
                customerInfo.add(info[1]);

                //add customerInfo to the list
                customerInfoList.add(customerInfo);

                //set customerInfo = null to start the new loop
                customerInfo =null;

            }

        }catch (FileNotFoundException e){
            System.out.println("Tệp không tồn tại.");
        }
        catch(IOException e) {

            e.printStackTrace();

        } finally {

            if(scanner != null) {

                scanner.close();

            }

        }
        return customerInfoList;
    }

    public static void main(String[] args) {
      //  TextFileService.readFile("store/customers.txt").forEach(System.out::println);

    }
}
