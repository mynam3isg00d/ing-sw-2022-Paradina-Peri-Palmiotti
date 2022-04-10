package Exceptions;

public class CloudEmptyException extends Exception{
    public String toString() {
        return "Accessed cloud is empty";
    }
}