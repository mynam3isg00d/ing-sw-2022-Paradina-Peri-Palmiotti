package Controller;

import Model.CloudWrapper;
import Model.Sack;
import Model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudControllerTest {

    @Test
    void fillClouds() throws Exception {
        CloudController cc = new CloudController(3);
        Sack s = new Sack(120);

        //gets the model to test its content
        CloudWrapper cm = cc.getCloudModel();

        //fills the clouds
        cc.fillClouds(s);

        //tests 4 students are now on the cloud and all students are correctly instantiated
        for (int i = 0; i < 3; i++) {
            List<Student> l = cm.peekAtCloud(i);
            assertEquals(4, l.size());
            for (Student ss : l) {
                assertNotNull(ss);
            }
        }
    }

    @Test
    void getFromCloud() throws Exception {
        CloudController cc = new CloudController(3);
        Sack s = new Sack(120);

        //gets the model to test its content
        CloudWrapper cm = cc.getCloudModel();

        //fills the clouds
        cc.fillClouds(s);

        //the list of students actually on cloud 0
        List<Student> expected = cm.peekAtCloud(0);
        try {
            //the list returned by the method in test
            List<Student> actual = cm.getFromCloud(0);

            //checks the size is equal
            assertEquals(expected.size(), actual.size());

            //checks the returned list is the expected one
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(0), actual.get(0));
            }

            //now the cloud should be empty
            assertTrue(cm.isEmpty(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}