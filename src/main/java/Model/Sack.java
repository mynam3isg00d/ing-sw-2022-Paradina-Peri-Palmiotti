package Model;
import Exceptions.EmptySackException;
import Network.JsonFactory;
import Observer.Observable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    /**
     * Sends the class to its observers
     */
    public void sendSack() {
        String s = new JsonFactory().modelToJson(this);
        notify(s);
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

        sendSack();
        return returnVal;
    }

    /**
     * Checks if the sack is empty
     * @return true if empty, else false
     */
    public boolean isEmpty() {return sack.empty();}

    /**
     * Converts an integer to a student: each integer corresponds to a student color
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

    /**
     * Gives a list with one student of each color
     * Used by character cards to play effects.
     * @return a list with one student of each color
     */
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


