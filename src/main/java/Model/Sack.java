package Model;
import java.util.*;

public class Sack {

    private Stack<Integer> sack;

    //Sack constructor parameters:
    //numOfStudents: number of starting students in the sack.
    public Sack(int numOfStudents) {
        sack = new Stack<Integer>();
        for(int i = 0; i < numOfStudents; i++) sack.push(i % 5);
        Collections.shuffle(sack);
    }


    //There is definitely a better way to do this using enums properly lmao
    //TODO: Define proper exceptions
    public Student draw() {
        int draw = sack.pop();
        return intToStudent(draw);
    }

    public boolean isEmpty() {return sack.empty();}

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


