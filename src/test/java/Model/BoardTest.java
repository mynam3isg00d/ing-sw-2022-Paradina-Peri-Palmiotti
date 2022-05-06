package Model;

import Exceptions.EmptyElementException;
import Exceptions.FullElementException;
import Exceptions.NoSuchStudentsException;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void generalPurposeTest() {
        Board b = new Board(8, 7);
        Sack s = new Sack(120);

        //entrance tests
        assertTrue(b.getEntrance() instanceof Student[]);
        for(int i=0; i<b.getEntrance().length; i++) {
            assertNull(b.getEntrance()[i]);
        }
        assertThrows(NoSuchStudentsException.class, () -> {b.removeFromEntrance(1);});
        assertDoesNotThrow(() -> {b.addToEntrance(s.draw(7));});
        assertThrows(FullElementException.class, () -> {b.addToEntrance(s.draw(1));});
        assertDoesNotThrow(() -> {b.removeFromEntrance(4);});


        //dining tests
        assertTrue(b.getDinings() instanceof int[]);
        for(int i=0; i<b.getDinings().length; i++) {
            assertEquals(0, b.getDinings()[i]);
        }
        assertDoesNotThrow(() -> {b.addToDining(Student.RED);});
        assertEquals(1, b.getDinings()[Student.RED.getColorId()]);
        assertDoesNotThrow(() -> {b.removeFromDining(Student.RED);});
        assertEquals(0, b.getDinings()[Student.RED.getColorId()]);
        assertThrows(EmptyElementException.class, () -> {b.removeFromDining(Student.RED);});
        for(int i=0; i<10; i++) {
            assertDoesNotThrow(() -> {b.addToDining(Student.PINK);});
        }
        assertThrows(FullElementException.class, () -> {b.addToDining(Student.PINK);});


        //professor tests
        //TODO: Exceptions not defined :p
        assertTrue(b.getProfessors() instanceof boolean[]);
        for(int i=0; i<b.getProfessors().length; i++) {
            assertFalse(b.getProfessors()[i]);
            b.addProfessor(i);
            assertTrue(b.getProfessors()[i]);
            b.removeProfessor(i);
            assertFalse(b.getProfessors()[i]);
        }


        //tower tests
        assertEquals(8, b.getTowersNum());
        assertThrows(FullElementException.class, () -> {b.addTower(1);});
        assertDoesNotThrow(() -> {b.removeTower(8);});
        assertThrows(EmptyElementException.class, () -> {b.removeTower(1);});
        assertThrows(FullElementException.class, () -> {b.addTower(9);});
        assertDoesNotThrow(() -> {b.addTower(8);});
    }
}