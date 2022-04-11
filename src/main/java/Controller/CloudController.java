package Controller;
import Exceptions.CloudEmptyException;
import Exceptions.CloudNotEmptyException;
import Exceptions.EmptySackException;
import Exceptions.InvalidStudentListException;
import Model.Cloud;
import Model.CloudWrapper;
import Model.Sack;
import Model.Student;

import java.util.*;

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

    /**
     * Fills all the clouds initialized in the model and handles the relative exceptions
     * @param s The Sack
     */
    public void fillClouds(Sack s) {
        for (int i = 0; i < numOfClouds; i++) {
            //draws from the sack 3 students in order to add them to the cloud
            try {
                List<Student> toAdd = s.draw(studentsPerCloud);

                //fills cloud i
                try {
                    cloudModel.fillCloud(toAdd, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Gets the students on the selected cloud and empties it. Also handles the exceptions that may be thrown
     * @param cloudIndex The selected island index
     * @return The list of students resulting from the operation
     */
    public List<Student> getFromCloud(int cloudIndex) {
        try {
            return cloudModel.getFromCloud(cloudIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Only used in testing
     * @return the reference to the model
     */
    public CloudWrapper getCloudModel() {
        return cloudModel;
    }
}
