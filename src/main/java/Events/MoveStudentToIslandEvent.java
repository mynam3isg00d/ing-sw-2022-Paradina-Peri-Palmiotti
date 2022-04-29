package Events;

import Model.Student;


public class MoveStudentToIslandEvent extends GameEvent {

    private Student student;
    private int islandID;

    public Student getStudent() {
        return student;
    }

    public int getIslandID() {
        return islandID;
    }

    @Override
    public void parseInput(String input) {
        String[] words = input.split("\\W+");
        student = Student.valueOf(words[0].toUpperCase());
        islandID = Integer.parseInt(words[1]);
    }

}
