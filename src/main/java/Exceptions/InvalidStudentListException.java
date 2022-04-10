package Exceptions;

public class InvalidStudentListException extends Exception{
    public String toString() {
        return "the student list just passed is invalid";
    }
}
