package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    @Test
    void addStudentsTest() {
        Island i = new Island();

        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.GREEN);
        toAdd.add(Student.RED);
        toAdd.add(Student.RED);
        i.addStudents(toAdd);

        int[] expected = new int[5];
        for (Student s : toAdd) {
            expected[s.getColorId()]++;
        }

        assertArrayEquals(expected, i.getStudents());

        List<Student> toAddNull = new ArrayList<>(); //empty list
        i.addStudents(toAddNull);
        assertArrayEquals(expected, i.getStudents());
    }

    @Test
    void motherNatureTest() {
        Island i = new Island();
        assertFalse(i.isMotherNature());

        i.setMotherNature(true);
        assertTrue(i.isMotherNature());
    }

    @Test
    void influenceTest() {
        Island i = new Island();
        assertNull(i.getInfluence());

        i.setInfluence(1);
        assertEquals(1, i.getInfluence());

        i.setInfluence(2);
        assertEquals(2, i.getInfluence());
    }

    @Test
    void mergeTest() {
        int[] expectedStudents = new int[5];

        Island i1 = new Island();

        //students that will be added to the first island
        List<Student> toAdd1 = new ArrayList<>();
        toAdd1.add(Student.YELLOW);
        expectedStudents[Student.YELLOW.getColorId()]++;
        toAdd1.add(Student.RED);
        expectedStudents[Student.RED.getColorId()]++;
        toAdd1.add(Student.RED);
        expectedStudents[Student.RED.getColorId()]++;
        toAdd1.add(Student.RED);
        expectedStudents[Student.RED.getColorId()]++;

        Island i2 = new Island();
        //students that will be added to the second island
        List<Student> toAdd2 = new ArrayList<>();
        toAdd2.add(Student.BLUE);
        expectedStudents[Student.BLUE.getColorId()]++;
        toAdd2.add(Student.GREEN);
        expectedStudents[Student.GREEN.getColorId()]++;
        toAdd2.add(Student.RED);
        expectedStudents[Student.RED.getColorId()]++;
        toAdd2.add(Student.RED);
        expectedStudents[Student.RED.getColorId()]++;

        //adding students to the two islands
        i1.addStudents(toAdd1);
        i2.addStudents(toAdd2);

        //in the game two island will be merged only when one has mother nature on it
        i1.setMotherNature(true);

        //in the game two island will be merged only when both have the same influence
        i1.setInfluence(Integer.valueOf(0));
        i2.setInfluence(Integer.valueOf(0));

        //merges the island
        Island i12 = new Island(i1, i2);


        assertAll(
                () -> assertEquals(2, i12.getDimension()),
                () -> assertEquals(true, i12.isMotherNature()),
                () -> assertArrayEquals(expectedStudents, i12.getStudents()),
                () -> assertEquals(0, i12.getInfluence())
                );
    }
}