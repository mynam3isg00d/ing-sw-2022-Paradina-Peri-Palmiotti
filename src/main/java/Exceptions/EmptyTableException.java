package Exceptions;

public class EmptyTableException extends Exception{
    public String getMessage() {
        return "Table is empty";
    }
}
