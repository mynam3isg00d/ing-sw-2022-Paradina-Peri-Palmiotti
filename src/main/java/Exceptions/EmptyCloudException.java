package Exceptions;

public class EmptyCloudException extends EmptyElementException {
    public String toString() {
        return "Accessed cloud is empty";
    }
}