package Events;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class MoveStudentToIslandEvent extends GameEvent {

    private Object student;
    private Object island;

    public Object getStudent() {
        return student;
    }

    public Object getIsland() {
        return island;
    }

    @Override
    public void parseInput(String input) {
        String[] words = input.split("\\W+");
        student = words[0];
        island = words[1];
    }

    @Override
    public List<Object> getInput() {
        List<Object> out = new ArrayList();
        out.add(student);
        out.add(island);
        return out;
    }
}
