package Model;

import Exceptions.FullElementException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudWrapperTest {

    @Test
    void construnctor() {
        CloudWrapper cw = new CloudWrapper(3, 3);
        for(int i=0; i<3; i++) {
            assertTrue(cw.isEmpty(i));
            try {
                cw.fillCloud(getStudentList(3), i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertFalse(cw.isEmpty(i));
        }
    }

    @Test
    void fillCloud() {
        CloudWrapper cw = new CloudWrapper(3, 3);
        assertThrows(Exception.class, () -> {cw.fillCloud(getStudentList(2), 1);});
        assertThrows(Exception.class, () -> {cw.fillCloud(getStudentList(4), 1);});
        assertDoesNotThrow(() -> {cw.fillCloud(getStudentList(3), 1);});
        assertThrows(Exception.class, () -> {cw.fillCloud(getStudentList(3), 1);});
    }

    @Test
    void generalCloudTesting() throws Exception {
        for(int numClouds = 2; numClouds < 5; numClouds++) {
            for(int numStudents = 3; numStudents < 5; numStudents++) {
                CloudWrapper cw = new CloudWrapper(numClouds, numStudents);
                List<Student> students = getStudentList(numStudents);

                for(int currCloud = 0; currCloud < numClouds; currCloud++) {
                    cw.fillCloud(students, currCloud);
                    List<Student> students0 = cw.peekAtCloud(currCloud);
                    assertFalse(cw.isEmpty(currCloud));

                    for(int i=0; i<students.size(); i++) {
                        assertEquals(students.get(i), students0.get(i));
                    }

                    List<Student> students1 = cw.getFromCloud(currCloud);
                    for(int i=0; i<students.size(); i++) {
                        assertEquals(students.get(i), students1.get(i));
                    }

                    assertTrue(cw.isEmpty(currCloud));
                }
            }
        }
    }

    private List<Student> getStudentList(int n) {
        List<Student> ret = new ArrayList<>();
        for(int i=0; i<n; i++) ret.add(Student.RED);
        return ret;
    }
}