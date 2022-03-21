package Model;
import java.util.*;

public class Board {

    private int[] diners;
    private List<Student> entrance;
    private boolean[] professors;
    private int towersNum;

    public Board() {
        //Davide: maybe convert static ArrayList-s to arrays? idk if it helps with overall efficency
        diners = new int[]{0, 0, 0, 0, 0};
        entrance = new ArrayList<Student>();
        professors = new boolean[]{false, false, false, false, false};
        /*TODO: this needs to be set accordingly to how many towers the board has to have. Maybe do it with
                an override of the constructor where there is parameter indicating how many players are playing

                D: i have no idea what this means
        */
        towersNum = 0;
    }

    public void addToDining(Student s){
        diners[s.getColorId()]++;
    }

    public void addToEntrance(List<Student> students){
        entrance.addAll(students);
    }
    public void addToEntrance(Student s){
        entrance.add(s);
    }

    //TODO: not sure this is written correctly in the UML: where do the removed students go??
    //
    //      To student heaven
    public void removeFromEntrance(List<Student> students){
        entrance.removeAll(students);
    }
    public void removeFromEntrance(Student s){
        entrance.remove(s);
    }

    //********getters*********//

    public int[] getDiners() {
        return diners;
    }

    public List<Student> getEntrance() {
        return entrance;
    }

    public boolean[] getProfessors() {
        return professors;
    }

    public int getTowersNum() {
        return towersNum;
    }

    //************************//


    //********setters*********//
    /*
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
     */
    //************************//
}