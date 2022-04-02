package Exceptions;

public class NoSuchStudentsException extends Exception{
    public String getMessage() {
        return "You Have No Such Students!!!";
    }
}
