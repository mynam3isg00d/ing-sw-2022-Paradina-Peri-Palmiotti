package Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void addToDining() {
        Board b = new Board(0);
        try {
            b.addToEntrance(Student.RED);
        } catch (Exception e){
            e.getMessage();
        }

        try {
            b.addToDining(Student.RED);
        } catch (Exception e){
            e.getMessage();
        }

        assertEquals(1, b.getDiners()[Student.RED.getColorId()]);



    }

    @Test
    void removeFromDining() {
    }

    @Test
    void addToEntrance() {
        Board b = new Board(0);
        try {
            b.addToEntrance(Student.RED);
        } catch (Exception e){
            e.getMessage();
        }

        List<Student> expected = new ArrayList<>();
        expected.add(Student.RED);

        try {
            b.addToEntrance(Student.BLUE);
            b.addToEntrance(Student.BLUE);
        } catch (Exception e){
            e.getMessage();
        }

        expected.add(Student.BLUE);
        expected.add(Student.BLUE);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), b.getEntrance().get(i));
        }
    }

    @Test
    void removeFromEntrance() {
        Board b = new Board(0);
        try {
            b.addToEntrance(Student.RED);
        } catch (Exception e){
            e.getMessage();
        }

        List<Student> expected = new ArrayList<>();
        expected.add(Student.RED);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), b.getEntrance().get(i));
        }
    }


    @Test
    void addTower() {
        Board b = new Board(0);
        b.addTower();
        b.addTower();

        assertEquals(2, b.getTowersNum());
    }

    @Test
    void removeTower() {
        Board b = new Board(8);
        b.removeTower();
        b.removeTower();

        assertEquals(6, b.getTowersNum());
    }
}