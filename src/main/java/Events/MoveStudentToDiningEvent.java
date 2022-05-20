package Events;

import Model.Student;

public class MoveStudentToDiningEvent extends GameEvent{

    private int studentIndex;

    public MoveStudentToDiningEvent() {
        eventId = "0002";
    }

    public MoveStudentToDiningEvent(String playerID, int studentIndex) {
        eventId = "0002";
        this.playerId = playerID;
        this.studentIndex = studentIndex;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    @Override
    public void parseInput(String input){
        this.studentIndex = Integer.parseInt(input);
    }

}
