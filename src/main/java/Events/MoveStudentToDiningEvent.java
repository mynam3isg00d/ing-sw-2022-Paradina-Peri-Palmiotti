package Events;

import Model.Student;

public class MoveStudentToDiningEvent extends GameEvent{

    private int studentIndex;

    public int getStudentIndex() {
        return studentIndex;
    }

    @Override
    public void parseInput(String input){
        this.studentIndex = Integer.parseInt(input);
    }

}
