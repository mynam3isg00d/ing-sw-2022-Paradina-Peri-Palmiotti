package Exceptions;

public class InvalidStudentListException extends Exception{
    public String getMessage() {
        return "the student list just passed is invalid";
    }
}
