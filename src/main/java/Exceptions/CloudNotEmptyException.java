package Exceptions;

public class CloudNotEmptyException extends Exception{
    public String getMessage() {
        return "Accessed cloud is not empty";
    }
}
