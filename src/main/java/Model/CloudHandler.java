package Model;
import java.util.*;

public class CloudHandler{

    private List<Cloud> clouds;

    public CloudHandler() {
        this.clouds = new ArrayList<Cloud>(3);
        for(Cloud c : clouds) {
            c.empty();
        }
    }

    public void fillClouds(Sack s){
        for(Cloud c : clouds){
            //TODO: randomly extract students from sack and put 3 on each cloud. Waiting for sack implementation
        }
    }

    public List<Student> getFromCloud(int cloudIndex){
        return clouds.get(cloudIndex).getStudents();
    }

}
