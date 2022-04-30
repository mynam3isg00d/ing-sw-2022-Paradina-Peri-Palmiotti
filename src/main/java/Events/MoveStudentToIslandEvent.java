package Events;

import Model.Student;


public class MoveStudentToIslandEvent extends GameEvent {
    private String playerId;

    private int studentBoardIndex;
    private Student student;
    private int islandID;

    public Student getStudent() {
        return student;
    }

    public int getIslandID() {
        return islandID;
    }

    public int getStudentBoardIndex() {
        return studentBoardIndex;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void parseInput(String input) {
        String[] words = input.split("\\W+");
        student = Student.valueOf(words[0].toUpperCase());
        islandID = Integer.parseInt(words[1]);
    }

}
