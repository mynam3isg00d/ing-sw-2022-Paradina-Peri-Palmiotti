package Exceptions;

public class CloudNotEmptyException extends Exception{
    public String toString() {
        return "Accessed cloud is not empty";
    }
}
