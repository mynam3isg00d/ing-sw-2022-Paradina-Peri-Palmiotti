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
            if(c.isEmpty()) {
                c.fill(s.draw(3));
            }
        }
    }

    public List<Student> getFromCloud(int cloudIndex){
        return clouds.get(cloudIndex).getStudents();
    }

}
