package Model;
import java.util.*;

public class Board {

    private ArrayList<Integer> diners;
    private List<Student> entrance;
    private ArrayList<Boolean> professors;
    private int towersNum;

    public Board() {
        //Davide: maybe convert static ArrayList-s to arrays? idk if it helps with overall efficency
        diners = new ArrayList<Integer>(5);
        entrance = new ArrayList<Student>();
        professors = new ArrayList<Boolean>(5);
        //TODO: this needs to be set accordingly to how many towers the board has to have. Maybe do it with
        //an override of the constructor where there is parameter indicating how many players are playing
        towersNum=0;
    }

    public void addToDining(int n, int colorID){
        //TODO: exception if diners.size()>5
        //adds 1 diner to the table corresponding to colorID
        diners.add(colorID, 1);
    }

    public void addToEntrance(List<Student> students){
        entrance.addAll(students);
    }

    //TODO: not sure this is written correctly in the UML: where do the removed students go??
    public void removeFromEntrance(List<Student> students){
        entrance.removeAll(students);
    }

    //********getters*********//

    public ArrayList<Integer> getDiners() {
        return diners;
    }

    public List<Student> getEntrance() {
        return entrance;
    }

    public ArrayList<Boolean> getProfessors() {
        return professors;
    }

    public int getTowersNum() {
        return towersNum;
    }

    //************************//


    //********setters*********//

    public void setDiners(ArrayList<Integer> diners) {
        this.diners = diners;
    }

    public void setEntrance(List<Student> entrance) {
        this.entrance = entrance;
    }

    public void setProfessors(ArrayList<Boolean> professors) {
        this.professors = professors;
    }

    public void setTowersNum(int towersNum) {
        this.towersNum = towersNum;
    }

    //************************//
}