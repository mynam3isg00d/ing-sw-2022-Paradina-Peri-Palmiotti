package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandsWrapperTest {

    @Test
    void mergeIslandsMiddle() {
        //initializes a new wrapper
        IslandsWrapper iw = new IslandsWrapper();

        //the list of students i want to add to one of the islands to merge
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        //sets students and influence on the two islands i'm trying to merge
        iw.addStudents(3, toAdd);
        iw.setInfluence(3, 1);
        iw.setInfluence(4, 1);

        //merges the islands
        iw.mergeIslands(3, 2);

        //---expected---
        //creates the expected list of islands resulting from the merge
        List<Island> expected = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            expected.add(new Island());
        }

        Island i1 = new Island();
        i1.setMotherNature(true);
        i1.addStudents(toAdd);
        i1.setInfluence(1);

        Island i2 = new Island();
        i2.setMotherNature(false);
        i2.setInfluence(1);

        try {
            expected.add(new Island(i1, i2));         //this is the island that should result from the merge
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        //the last islands, unchanged
        for(int i = 4; i<11; i++) {
            expected.add(new Island());
        }


        expected.get(0).setMotherNature(true);
        //---------

        assertEquals(11, iw.getIslandLength());
        for (int i = 0; i < 11; i++) {
            assertEquals(expected.get(i).getInfluence(), iw.getIsland(i).getInfluence());
            assertArrayEquals(expected.get(i).getStudents(), iw.getIsland(i).getStudents());
            assertEquals(expected.get(i).getDimension(), iw.getIsland(i).getDimension());
            assertEquals(expected.get(i).isMotherNature(), iw.getIsland(i).isMotherNature());
        }
    }

    @Test
    void mergeIslandsOnEdge() {
        //initializes a new wrapper
        IslandsWrapper iw = new IslandsWrapper();

        //the list of students i want to add to one of the islands to merge
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        //sets students and influence on the two islands i'm trying to merge
        iw.addStudents(11, toAdd);
        iw.setInfluence(11, 1);
        iw.setInfluence(0, 1);

        //merges the islands
        iw.mergeIslands(11, 2);


        //---expected---
        List<Island> expected = new ArrayList<>();
        for (int i = 0; i<10; i++) {
            Island ni = new Island();
            expected.add(ni);
        }

        Island i1 = new Island();
        Island i2 = new Island();

        i1.addStudents(toAdd);
        i1.setInfluence(1);
        i2.setInfluence(1);

        i1.setMotherNature(true);
        i2.setMotherNature(false);

        try {
            Island newIsland = new Island(i1, i2);
            expected.add(newIsland);         //this is the island that should result from the merge
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        //expected end

        expected.get(10).setMotherNature(true);
        assertEquals(11, iw.getIslandLength());
        for (int i = 0; i < 11; i++) {
            //System.out.println("ACTUAL");
            //System.out.println(iw.getIsland(i));
            //System.out.println("EXPECTED " + i);
            //System.out.println(expected.get(i));
            assertEquals(expected.get(i).getInfluence(), iw.getIsland(i).getInfluence());
            assertArrayEquals(expected.get(i).getStudents(), iw.getIsland(i).getStudents());
            assertEquals(expected.get(i).getDimension(), iw.getIsland(i).getDimension());
            assertEquals(expected.get(i).isMotherNature(), iw.getIsland(i).isMotherNature());
        }
    }

    @Test
    void mergeThreeIslandsMiddle() {
        //initializes a new wrapper
        IslandsWrapper iw = new IslandsWrapper();

        //the list of students i want to add to one of the islands to merge
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        //sets students and influence on the two islands i'm trying to merge
        iw.addStudents(3, toAdd);
        iw.setInfluence(3, 1);
        iw.setInfluence(4, 1);
        iw.setInfluence(5, 1);

        //merges the islands
        iw.mergeIslands(3, 3);

        //---expected---
        //creates the expected list of islands resulting from the merge
        List<Island> expected = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            expected.add(new Island());
        }

        Island i1 = new Island();
        i1.setMotherNature(true);
        i1.addStudents(toAdd);
        i1.setInfluence(1);

        Island i2 = new Island();
        i2.setMotherNature(false);
        i2.setInfluence(1);

        Island i3 = new Island();
        i3.setMotherNature(false);
        i3.setInfluence(1);

        try {
            Island ni = new Island(i1, i2);
            ni = new Island(ni, i3);
            expected.add(ni);         //this is the island that should result from the merge
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        //the last islands, unchanged
        for(int i = 3; i<10; i++) {
            expected.add(new Island());
        }


        expected.get(0).setMotherNature(true);
        //---------

        assertEquals(10, iw.getIslandLength());
        for (int i = 0; i < 10; i++) {
            assertEquals(expected.get(i).getInfluence(), iw.getIsland(i).getInfluence());
            assertArrayEquals(expected.get(i).getStudents(), iw.getIsland(i).getStudents());
            assertEquals(expected.get(i).getDimension(), iw.getIsland(i).getDimension());
            assertEquals(expected.get(i).isMotherNature(), iw.getIsland(i).isMotherNature());
        }
    }

    @Test
    void mergeThreeIslandsOnEdge() {
        //initializes a new wrapper
        IslandsWrapper iw = new IslandsWrapper();

        //the list of students i want to add to one of the islands to merge
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.YELLOW);

        //sets students and influence on the two islands i'm trying to merge
        iw.addStudents(11, toAdd);
        iw.setInfluence(11, 1);
        iw.setInfluence(0, 1);
        iw.setInfluence(10, 1);

        //merges the islands
        iw.mergeIslands(10, 3);


        //---expected---
        List<Island> expected = new ArrayList<>();
        for (int i = 0; i<9; i++) {
            Island ni = new Island();
            expected.add(ni);
        }

        Island i1 = new Island();
        Island i2 = new Island();
        Island i3 = new Island();

        i1.addStudents(toAdd);
        i1.setInfluence(1);
        i2.setInfluence(1);
        i3.setInfluence(1);

        i1.setMotherNature(true);
        i2.setMotherNature(false);
        i3.setMotherNature(false);

        try {
            Island newIsland = new Island(i1, i2);
            newIsland = new Island(newIsland, i3);
            expected.add(newIsland);         //this is the island that should result from the merge
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        expected.get(9).setMotherNature(true);
        //expected end

        assertEquals(10, iw.getIslandLength());
        for (int i = 0; i < 10; i++) {
            //System.out.println("ACTUAL");
            //System.out.println(iw.getIsland(i));
            //System.out.println("EXPECTED " + i);
            //System.out.println(expected.get(i));
            assertEquals(expected.get(i).getInfluence(), iw.getIsland(i).getInfluence());
            assertArrayEquals(expected.get(i).getStudents(), iw.getIsland(i).getStudents());
            assertEquals(expected.get(i).getDimension(), iw.getIsland(i).getDimension());
            assertEquals(expected.get(i).isMotherNature(), iw.getIsland(i).isMotherNature());
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