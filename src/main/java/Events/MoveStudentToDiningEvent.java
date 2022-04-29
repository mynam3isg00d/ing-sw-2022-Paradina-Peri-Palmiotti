package Events;

import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class MoveStudentToDiningEvent extends GameEvent{

    private int studentIndex;

    public int getStudentIndex() {
        return studentIndex;
    }

    @Override
    public void parseInput(String input){
        try{
            this.studentIndex = Integer.parseInt(input);
        }catch (NumberFormatException e){
            e.printStackTrace();;
        }
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(studentIndex);
        return out;
    }
}
