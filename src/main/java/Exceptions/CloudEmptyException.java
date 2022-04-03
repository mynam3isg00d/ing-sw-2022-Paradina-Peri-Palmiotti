package Exceptions;

public class CloudEmptyException extends Exception{
    public String getMessage() {
        return "Accessed cloud is empty";
    }
}