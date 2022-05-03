package Events;

import Model.Student;


public class MoveStudentToIslandEvent extends GameEvent {

    public MoveStudentToIslandEvent() {
        eventId = "0003";
    }
    private int studentBoardIndex;
    private int islandID;


    public int getIslandID() {
        return islandID;
    }

    public int getStudentBoardIndex() {
        return studentBoardIndex;
    }

    @Override
    public void parseInput(String input) {
        String[] words = input.split("\\W+");
        studentBoardIndex = Integer.parseInt(words[0]);
        islandID = Integer.parseInt(words[1]);
    }

}
