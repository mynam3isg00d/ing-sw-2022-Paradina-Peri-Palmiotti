package Events;

import Model.Student;

public class MoveStudentToDiningEvent extends GameEvent{

    private Student student;

    public Student getStudent() {
        return student;
    }

    @Override
    public void parseInput(String input){
        this.student = Student.valueOf(input);
    }

}
