package Model;

import Exceptions.EmptyCloudException;
import Exceptions.CloudNotEmptyException;
import Exceptions.InvalidStudentListException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test
    void cloudConstructorTest() {
        Cloud c = new Cloud(453);
        assertNotNull(c.peek());
        assertTrue(c.isEmpty());
    }

    @Test
    void fillTest() throws InvalidStudentListException, CloudNotEmptyException {
        Cloud c = new Cloud(4);
        assertThrows(InvalidStudentListException.class, () -> {c.fill(getStudents(3));});
        assertThrows(InvalidStudentListException.class, () -> {c.fill(getStudents(5));});
        c.fill(getStudents(4));
        assertThrows(CloudNotEmptyException.class, () -> {c.fill(getStudents(4));});
    }

    @Test
    void emptyTest() throws InvalidStudentListException, CloudNotEmptyException, EmptyCloudException {
        Cloud c = new Cloud(3);
        assertThrows(EmptyCloudException.class, c::empty);
        c.fill(getStudents(3));
        ArrayList<Student> ret = (ArrayList<Student>)c.empty();
        assertThrows(EmptyCloudException.class, c::empty);
        assertEquals(3, ret.size());
    }
    private static List<Student> getStudents(int num) {
        ArrayList<Student> ret = new ArrayList<>();
        for(int i=0; i<num; i++) {
            ret.add(Student.BLUE);
        }
        return ret;
    }
}