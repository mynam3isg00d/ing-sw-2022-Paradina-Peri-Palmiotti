package Exceptions;

public class WizardAlreadyChosenException extends Exception{
    @Override
    public String toString() {
        return "This wizard has already been chosen";
    }
}
