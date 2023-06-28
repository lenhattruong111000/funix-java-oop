package asm01;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Asm01 {

    private static Scanner scanner =new Scanner(System.in);
    private static final String FEMALE = "Nu";
    private static final String MALE = "Nam";

    public static void main(String[] args) {
        //show menu
        menu();

        //run program
        programRunning();
    }

    // function for create a menu
    public static void menu(){
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| NGAN HANG SO | FX21573@v1.0.0                 |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("| 1. Nhap CCCD                                  |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.print("Chuc nang: ");
    }

    public static void menuForCheckFunction(){
        System.out.println("\t| 1. Kiem Tra noi sinh");
        System.out.println("\t| 2. Kiem tra tuoi, gioi tinh");
        System.out.println("\t| 3. Kiem tra so ngau nhien");
        System.out.println("\t| 0. Thoat");
        System.out.print("Chuc nang: ");

    }

    //function for exit option.
    // option: 0
    public static void exit(){
        menu();
    }

    // Location and it's id
    public static Map<String, String> idAndlocation(){
        Map<String, String> idLocal = new HashMap<String, String>();
        idLocal.put("001","Ha Noi");
        idLocal.put("002","Ha Giang");
        idLocal.put("004","Cao Bang");
        idLocal.put("006","Bac Kan");
        idLocal.put("008","Tuyen Quang");
        idLocal.put("010","Lao Cai");
        idLocal.put("011","Dien Bien");
        idLocal.put("012","Lai Chau");
        idLocal.put("014","Son La");
        idLocal.put("015","Yen Bai");
        idLocal.put("017","Hoa Binh");
        idLocal.put("019","Thai Nguyen");
        idLocal.put("020","lang Son");
        idLocal.put("022","Quang Ninh");
        idLocal.put("024","Bac Giang");
        idLocal.put("025","Phu Tho");
        idLocal.put("026","Vinh Phuc");
        idLocal.put("027","Bac Ninh");
        idLocal.put("030","Hai Duong");
        idLocal.put("031","Hai Phong");
        idLocal.put("033","Hung Yen");
        idLocal.put("034","Thai Binh");
        idLocal.put("035","Ha Nam");
        idLocal.put("036","Nam Dinh");
        idLocal.put("037","Ninh Binh");
        idLocal.put("038","Thanh Hoa");
        idLocal.put("040","Nghe An");
        idLocal.put("042","Ha Tinh");
        idLocal.put("044","Quang Binh");
        idLocal.put("045","Quang Tri");
        idLocal.put("046","Thua Thien Hue");
        idLocal.put("048","Da Nang");
        idLocal.put("049","Quang Nam");
        idLocal.put("051","Quang Ngai");
        idLocal.put("052","Binh Dinh");
        idLocal.put("054","Phu Yen");
        idLocal.put("056","Khanh Hoa");
        idLocal.put("058","Ninh Thuan");
        idLocal.put("060","Binh Thuan");
        idLocal.put("062","Kom Tum");
        idLocal.put("064","Gia Lai");
        idLocal.put("066","Dak Lak");
        idLocal.put("067","Dak Nong");
        idLocal.put("068","Lam Dong");
        idLocal.put("070","Binh Phuoc");
        idLocal.put("072","Tay Ninh");
        idLocal.put("074","Binh Duong");
        idLocal.put("075","Dong Nai");
        idLocal.put("077","Ba Ria - Vung Tau");
        idLocal.put("079","Ho Chi Minh");
        idLocal.put("080","Long An");
        idLocal.put("082","Tien Giang");
        idLocal.put("083","Ben Tre");
        idLocal.put("084","Tra Vinh");
        idLocal.put("086","Vinh Long");
        idLocal.put("087","Dong Thap");
        idLocal.put("089","An Giang");
        idLocal.put("091","Kien Giang");
        idLocal.put("092","Can Tho");
        idLocal.put("093","Hau Giang");
        idLocal.put("094","Soc Trang");
        idLocal.put("095","Bac Lieu");
        idLocal.put("096","Ca Mau");

        return idLocal;
    }

    //function for check valid idProvince
    public static boolean isValidIdProvince(String idProvince){
        Map<String, String> idLocations = idAndlocation();

        for ( Map.Entry<String, String> idLocation: idLocations.entrySet()) {
            if (idLocation.getKey().equals(idProvince)){
                return true;
                //cannot using break; with for each
                //break;
            }
        }
        return false;
    }

    public static String getLocation(String idCCCD){
        String idLocation = idCCCD.substring(0, 3);
        Map<String, String> idLocaations = idAndlocation();
        for (Map.Entry<String, String> idlocal: idLocaations.entrySet()) {
            if (idLocation.equals(idlocal.getKey())){
                System.out.println(idlocal.getKey());
                return  idlocal.getValue();
            }
        }
        return null;

    }

    public static String getGender(String idCCCD){
        String idGender =idCCCD.substring(3,4);

        if (Integer.parseInt(idGender)%2==0){
            return MALE;
        }
        return FEMALE;
    }
    //get century denpend on idGender
    public static int getCentury(String idCCCD){
        String idGender =idCCCD.substring(3,4);
        int century=0;
        switch (idGender){
            case "0":
            case "1":
                century=20;
                break;
            case "2":
            case "3":
                century=21;
                break;
            case "4":
            case "5":
                century=22;
                break;
            case "6":
            case "7":
                century=23;
                break;
            case "8":
            case "9":
                century=24;
                break;
            default:
                return 0;
        }

        return century;
    }

    public static String getYearOfBirth(String idCCCD){
        int century = getCentury(idCCCD);
        String idYearOfBirth =idCCCD.substring(4,6);

        return String.valueOf(century-1)+idYearOfBirth;
    }
    public static String getIdRandomNum(String idCCCD){
        String idRandomNum = idCCCD.substring(6);
        return  idRandomNum;
    }

    //function for getting the Identify Card Number(CCCD)
    public static String getIdNum(){
        System.out.print("Moi Ban Nhap Ma CCCD: ");
        String id = scanner.next();
        return id;
    }

    public  static boolean isValidIdNum(String idCCCD){
        //regex to check the string with full 12 digits number
        //the string is not consist of [a-z][A-Z] and special characters
        // '^' matches  with the head of string
        // '$' matches with the head of string
        // [0-9]{12} String must have number from 0 to 9
        // and must have 12 digits number
        String regex ="^[0-9]{12}$";
        //using subString to get first 3 digits(id of Location)
        if (idCCCD.matches(regex) ) {
            String idProvince = idCCCD.substring(0, 3);

            if (isValidIdProvince(idProvince)){
                return true;
            }
        }
        return false;
    }

    //function for verification code
    public static  int generateVerificationCode(){
        int verificationCode = 100+ (int)Math.floor(Math.random()*899);
        System.out.println("Nhap ma xac thuc: "+ verificationCode);
        return verificationCode;
    }

    //get verification code from user
    public  static int getInputCode(){
        String regex ="^[0-9]{3}$";
        System.out.print("Vui long nhap ma xac thuc: ");
        String inputCode =scanner.next();
        if (inputCode.matches(regex))
            return Integer.parseInt(inputCode);
        return 0;
    }

    //function for check valid verification code
    public  static  boolean isValidCode(int generateCode, int inputCode){
        if(generateCode== inputCode) return true;
        return false;
    }

    //function for program running
    public  static void programRunning(){
        // option = 0 mean: exit
        // option = 1 mean input Identify Card Number
        while (scanner.hasNext()){
            //int option = scanner.nextInt();
            String option =scanner.next();
            if(option.equals("0")) exit();
            else if(option.equals("1")){
                // input verification code
                int verificationCode =generateVerificationCode();
                int inputCode =getInputCode();

                // check valid verification code
                while (!isValidCode(verificationCode, inputCode))  {
                    System.out.println("+----------+-------------------------+----------+");
                    System.out.println("| Ma xac thuc khong dung. Vui long nhap lai.    |");
                    System.out.println("| 1. Nhap so 1 de nhap lai ma xac thuc.         |");
                    System.out.println("| 2. Hoac Nhap so 0 de thoat.                   |");
                    System.out.println("+----------+-------------------------+----------+");
                    System.out.print("Chuc nang: ");

                    option =scanner.next();
                    if (option.equals("0")){
                        exit();
                        break;
                    } else if (option.equals("1")){

                        inputCode = getInputCode();
                    }
                }

                // if verification is true do next step
                if (isValidCode(verificationCode, inputCode)){
                    String idCCCD =getIdNum();

                    // check CCCD is valid or not
                    while (!isValidIdNum(idCCCD)){
                        System.out.println("+----------+-------------------------+----------+");
                        System.out.println("| Ma CCCD Khong Hop Le.                         |");
                        System.out.println("| 1. Nhap so 1 de nhap lai ma CCCD.             |");
                        System.out.println("| 2. Hoac Nhap 'No' de thoat.                   |");
                        System.out.println("+----------+-------------------------+----------+");
                        System.out.print("Chuc nang: ");


                        option = scanner.next();
                        if (option.equals("No")){
                            exit();
                            break;
                        }else if (option.equals("1")){
                            idCCCD= getIdNum();
                        }
                    }
                    while (isValidIdNum(idCCCD)){
                        menuForCheckFunction();
                        option=scanner.next();

                        if (option.equals("1")){
                            System.out.println("Noi Sinh: "+getLocation(idCCCD));
                        }else if (option.equals("2")){
                            System.out.println("Gioi tinh: "+getGender(idCCCD)+" | "+ getYearOfBirth(idCCCD));
                        } else if (option.equals("3")) {
                            System.out.println("So ngau nhien: "+ getIdRandomNum(idCCCD));
                        } else if (option.equals("0")) {
                            exit();
                            break;

                        }
                    }
                }
            }
        }

    }
}
