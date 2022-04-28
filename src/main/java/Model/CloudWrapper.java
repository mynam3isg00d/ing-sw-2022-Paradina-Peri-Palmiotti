package Model;

import Exceptions.CloudEmptyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CloudWrapper extends Observable {
    private List<Cloud> clouds;

    /**
     * Initializes clouds according to the number passed as a parameter
     * @param numOfClouds The number of clouds needed, decided by CloudController
     * @param studentsPerCloud The maximum number of Students per cloud, decided by CloudController
     */
    public CloudWrapper(int numOfClouds, int studentsPerCloud) {
        clouds = new ArrayList<>();
        for (int i = 0; i < numOfClouds; i++) {
            clouds.add(new Cloud(studentsPerCloud));
        }
    }

    /**
     * Fills the selected cloud with the provided Students
     * @param students A list of students drawn from the sack (in CloudController)
     * @param cloudIndex The index of the selected cloud
     * @throws Exception Generic Exception that could come from a single cloud, will be handled in the controller
     */
    public void fillCloud(List<Student> students, int cloudIndex) throws Exception{
        clouds.get(cloudIndex).fill(students);
    }

    /**
     * Empties the selected cloud and returns its content
     * @param cloudIndex The index of the selected island
     * @return The list of students previously on the cloud
     * @throws CloudEmptyException Thrown if the selected island is already empty
     */
    public List<Student> getFromCloud(int cloudIndex) throws CloudEmptyException {
        return clouds.get(cloudIndex).empty();
    }

    /**
     * Returns a copy of the content of the selected cloud
     * Mainly used for testing
     * @param cloudIndex The index of the selected cloud
     * @return A copy of the List of Students on the cloud
     */
    public List<Student> peekAtCloud (int cloudIndex) {
        return clouds.get(cloudIndex).peek();
    }

    /**
     * Tells if the selected cloud has any students on top
     * @param cloudIndex The index of the selected cloud
     * @return True if the selected cloud is empty, False otherwise
     */
    public boolean isEmpty(int cloudIndex) {
        return clouds.get(cloudIndex).isEmpty();
    }
}
