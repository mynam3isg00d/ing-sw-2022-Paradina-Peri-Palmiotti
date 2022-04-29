package Events;

import java.util.ArrayList;
import java.util.List;

public class MoveStudentToDiningEvent extends GameEvent{

    private Object student;

    public Object getStudent() {
        return student;
    }

    @Override
    public void parseInput(String input) {
        student = input;
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(student);
        return out;
    }
}
