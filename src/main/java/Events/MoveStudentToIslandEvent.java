package Events;

import Model.Student;


public class MoveStudentToIslandEvent extends GameEvent {

    private int studentBoardIndex;
    private int islandID;

    public MoveStudentToIslandEvent() {
        eventId = "0003";
    }

    public MoveStudentToIslandEvent(String playerID, int studentBoardIndex, int islandID) {
        eventId = "0003";
        this.playerId = playerID;
        this.studentBoardIndex = studentBoardIndex;
        this.islandID = islandID;
    }


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
