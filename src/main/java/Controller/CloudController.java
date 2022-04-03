package Controller;
import Exceptions.EmptySackException;
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


    public void fillClouds(Sack s) throws EmptySackException {
        for(Cloud c : clouds){
            if(c.isEmpty()) {
                c.fill(s.draw(3));
            }
        }
    }

    //TODO: remove students from the cloud
    public List<Student> getFromCloud(int cloudIndex){
        return clouds.get(cloudIndex).empty();
    }
}
