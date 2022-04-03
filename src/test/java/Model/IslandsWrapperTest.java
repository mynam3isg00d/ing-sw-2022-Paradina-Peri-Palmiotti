package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandsWrapperTest {

    @Test
    void mergeIslands() {
        IslandsWrapper iw = new IslandsWrapper();

        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        iw.addStudents(3, toAdd);
        iw.setInfluence(3, 1);
        iw.setInfluence(4, 1);

        iw.mergeIslands(3, 2);

        //---expected---
        List<Island> expected = new ArrayList<>();
        for (int i = 0; i<2; i++) {
            expected.add(new Island());
        }

        Island i1 = new Island();
        i1.setMotherNature(true);
        i1.addStudents(toAdd);
        i1.setInfluence(1);

        Island i2 = new Island();
        i2.setMotherNature(false);
        i2.setInfluence(1);

        expected.add(new Island(i1, i2));

        for(int i = 4; i<11; i++) {
            expected.add(new Island());
        }


        expected.get(0).setMotherNature(true);
        //---------

        assertEquals(11, iw.getIslandLength());
        for (int i = 0; i < 11; i++) {
            assertEquals(expected.get(i), iw.getIsland(i));
        }
    }

    @Test
    void addStudents() {
        IslandsWrapper iw = new IslandsWrapper();

        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        iw.addStudents(0, toAdd);
    }

    @Test
    void moveMotherNature() {
    }
}