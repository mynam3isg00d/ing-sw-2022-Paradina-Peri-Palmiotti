package Controller;
import Exceptions.CloudEmptyException;
import Exceptions.CloudNotEmptyException;
import Exceptions.EmptySackException;
import Exceptions.InvalidStudentListException;
import Model.Cloud;
import Model.Sack;
import Model.Student;

import java.util.*;

public class CloudController {

    private List<Cloud> clouds;

    public CloudController(int numOfPlayers) {
        this.clouds = new ArrayList<>();

        int numOfStudents;
        if (numOfPlayers == 2 || numOfPlayers == 4) {
            numOfStudents = 3;
        } else {
            numOfStudents = 4;
        }

        for (int i=0; i<numOfPlayers; i++) {
            clouds.add(new Cloud(numOfStudents));
        }
    }


    public void fillClouds(Sack s) {
        for(Cloud c : clouds){
            if(c.isEmpty()) {
                try {
                    c.fill(s.draw(3));
                } catch (CloudNotEmptyException | InvalidStudentListException | EmptySackException e) {
                    e.getMessage();
                }
            }
        }
    }

    //TODO: remove students from the cloud
    public List<Student> getFromCloud(int cloudIndex) {
        try {
            return clouds.get(cloudIndex).empty();
        } catch (CloudEmptyException e) {
            e.getMessage();
        }
        return null;
    }
}
