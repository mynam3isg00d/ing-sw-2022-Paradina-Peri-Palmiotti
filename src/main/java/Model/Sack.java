package Model;
import Exceptions.EmptySackException;

import java.util.*;

public class Sack {

    private final Stack<Integer> sack;

    //Sack constructor parameters:
    //numOfStudents: number of starting students in the sack.
    public Sack(int numOfStudents) {
        sack = new Stack<>();
        for(int i = 0; i < numOfStudents; i++) sack.push(i % 5);
        Collections.shuffle(sack);
    }

    //TODO: Define proper exceptions
    public List<Student> draw(int numOfStudents) throws EmptySackException {
        List<Student> returnVal = new ArrayList<>();
        for (int i=0; i<numOfStudents; i++) {
            int draw;
            try {
                draw = sack.pop();
                returnVal.add(intToStudent(draw));
            } catch (EmptyStackException e) {
                throw new EmptySackException();
            }
        }
        return returnVal;
    }

    public boolean isEmpty() {return sack.empty();}

    //There is definitely a better way to do this using enums properly lmao
    public static Student intToStudent(int i) {
        switch (i) {
            case 0:
                return Student.YELLOW;
            case 1:
                return Student.BLUE;
            case 2:
                return Student.GREEN;
            case 3:
                return Student.RED;
            case 4:
                return Student.PINK;
            default:
                return null;
        }
    }
}


