package Model;

import Exceptions.EmptySackException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SackTest {

    @Test
    void IntToStudentTest() {
        assertAll(  () -> assertEquals(Student.YELLOW, Sack.intToStudent(0)),
                    () -> assertEquals(Student.BLUE, Sack.intToStudent(1)),
                    () -> assertEquals(Student.GREEN, Sack.intToStudent(2)),
                    () -> assertEquals(Student.RED, Sack.intToStudent(3)),
                    () -> assertEquals(Student.PINK, Sack.intToStudent(4))
                );
    }

    @Test
    void drawTest() throws EmptySackException {
        List<Student> actual = (new Sack(120)).draw(5);
        assertEquals(5, actual.size());
        for(Student s : actual) {
            assertNotNull(s);
        }
    }

    @Test
    void drawTooMuch() {
        Exception e = assertThrows(EmptySackException.class, () -> {(new Sack(10)).draw(15);});
    }

    @Test
    void multipleDraws() throws EmptySackException {
        Sack s = new Sack(30);
        assertEquals(10, s.draw(10).size());
        assertEquals(5, s.draw(5).size());
        Exception e = assertThrows(EmptySackException.class, () -> {s.draw(20);});
    }

}