package asm04.exception;

public class CustomerIdNotValidException extends RuntimeException{
    public  CustomerIdNotValidException(String message){
        super(message);
    }
}
