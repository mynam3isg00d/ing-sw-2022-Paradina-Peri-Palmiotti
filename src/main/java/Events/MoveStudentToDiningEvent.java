package Events;

import Model.Student;

public class MoveStudentToDiningEvent extends GameEvent{

    public MoveStudentToDiningEvent() {
        eventId = "0002";
    }
    private int studentIndex;

    public int getStudentIndex() {
        return studentIndex;
    }

    @Override
    public void parseInput(String input){
        this.studentIndex = Integer.parseInt(input);
    }

}
