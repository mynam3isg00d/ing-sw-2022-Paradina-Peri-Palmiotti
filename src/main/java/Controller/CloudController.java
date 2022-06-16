package Controller;
import Exceptions.EmptyCloudException;
import Model.CloudWrapper;
import Model.Sack;
import Model.Student;
import Observer.View.RemoteView;

import java.util.*;

/**
 * The Controller in charge of the operations regarding the clouds.
 */

public class CloudController {
    private CloudWrapper cloudModel;
    private int numOfClouds;
    private int studentsPerCloud;

    /**
     * Initializes CloudController and the clouds model
     * Acts different accordingly to the number of players in the game
     * @param numOfPlayers The number of players playing
     */
    public CloudController(int numOfPlayers) {
        numOfClouds = numOfPlayers;
        if (numOfPlayers == 2 || numOfPlayers == 4) {
            studentsPerCloud = 3;
        } else {
            studentsPerCloud = 4;
        }

        cloudModel = new CloudWrapper(numOfClouds, studentsPerCloud);
    }

    public void addObserverToModel(RemoteView rv) {
        cloudModel.addObserver(rv);
    }

    /**
     * Fills all the clouds initialized in the model and handles the relative exceptions
     * @param s The Sack
     */
    public void fillClouds(Sack s) throws Exception {
        for (int i = 0; i < numOfClouds; i++) {
            //draws from the sack 3 students in order to add them to the cloud
            List<Student> toAdd = s.draw(studentsPerCloud);

            //fills cloud i
            cloudModel.fillCloud(toAdd, i);
        }

    }

    /**
     * Gets the students on the selected cloud and empties it. Also handles the exceptions that may be thrown
     * @param cloudIndex The selected island index
     * @return The list of students resulting from the operation
     */
    public List<Student> getFromCloud(int cloudIndex) throws EmptyCloudException {
        return cloudModel.getFromCloud(cloudIndex);
    }


    /**
     * Only used in testing
     * @return the reference to the model
     */
    public CloudWrapper getCloudModel() {
        return cloudModel;
    }

    public int getNumOfClouds() {
        return numOfClouds;
    }
}
