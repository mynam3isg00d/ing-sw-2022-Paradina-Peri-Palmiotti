package Model;
import Exceptions.EmptySackException;

import java.util.*;

public class Sack extends Observable {

    private final Stack<Integer> sack;

    /**
     * Sack constructor, NOTE: adds students like
     * R, B, Y, G, P, R, B, Y, G, P.... and then shuffles (keep in mind for more color implementation)
     * @param numOfStudents the number of students initially in the sack
     */
    public Sack(int numOfStudents) {
        sack = new Stack<>();

        for(int i = 0; i < numOfStudents; i++) sack.push(i % 5);
        Collections.shuffle(sack);
    }

    public void sendSack() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Used for drawing students from the bag
     * @param numOfStudents the number of students to draw
     * @return an arraylist of students drawn
     * @throws EmptySackException thrown if the sack is empty
     */
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

    /**
     * Checks if the sack is empty
     * @return true if empty, else false
     */
    public boolean isEmpty() {return sack.empty();}

    //TODO: make this better
    /**
     * int to student converter
     * @param i number (0, 4) for color id
     * @return corresponding color
     */
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

    public int getSackSize() {return sack.size();}

    public static List<Student> getColors() {
        List<Student> x = new ArrayList<>();
        x.add(Student.YELLOW);
        x.add(Student.BLUE);
        x.add(Student.GREEN);
        x.add(Student.RED);
        x.add(Student.PINK);

        return x;
    }
}


