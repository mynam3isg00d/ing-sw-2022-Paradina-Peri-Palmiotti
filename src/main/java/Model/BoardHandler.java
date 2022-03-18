package Model;

import java.util.List;

public class BoardHandler {
    private Board board;
    private Player player;

    public BoardHandler(Player player){
        this.player = player;
    }

    public void moveToDiningRoom(List<Student> students){

        this.board.removeFromEntrance(students);

        for(Student s : students){
            this.board.addToDining(1, s.getColorId());
        }

    }


    public void fillEntrance(Sack s){
        //TODO: randomly draw 7 students from sack and put them in entrance. Waiting for Sack implementation
    }
}
