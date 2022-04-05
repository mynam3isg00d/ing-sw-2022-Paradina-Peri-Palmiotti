package Controller;

import Model.Student;

import java.util.ArrayList;
import java.util.List;

public class Eryantis {
    public static List<Student> getColors() {
        List<Student> x = new ArrayList<>();
        x.add(Student.YELLOW);
        x.add(Student.BLUE);
        x.add(Student.GREEN);
        x.add(Student.RED);
        x.add(Student.PINK);

        return x;
    }
}
