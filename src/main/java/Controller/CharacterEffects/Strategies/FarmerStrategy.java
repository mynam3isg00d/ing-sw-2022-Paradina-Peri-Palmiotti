package Controller.CharacterEffects.Strategies;

import Model.Board;
import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FarmerStrategy implements ProfessorStrategy{
    public void updateProfessors(Player[] professors, HashMap<String, Board> playerBoardMap, List<Player> players) {

        Player[] old_professors = professors.clone();

        int[] maxNumOfStudents = new int[5];
        for(int i=0; i<5; i++) {
            if (old_professors[i] == null) {
                maxNumOfStudents[i] = 0;
            } else {
                maxNumOfStudents[i] = playerBoardMap.get(old_professors[i].getPlayerID()).getDinings()[i];
            }
        }

        for(Player p : players) {
            int[] diners = playerBoardMap.get(p.getPlayerID()).getDinings();
            for(int i=0; i<5; i++) {
                //<=
                if (maxNumOfStudents[i] <= diners[i]) {
                    professors[i] = p;
                    maxNumOfStudents[i] = diners[i];
                }
            }
        }

        for(int i=0; i<professors.length; i++) {
            if (professors[i] != null) {
                String newOwner = professors[i].getPlayerID();
                if(old_professors[i] == null) {
                    //There is no old owner, simply add the new professor
                    Board newB = playerBoardMap.get(newOwner);
                    newB.addProfessor(i);
                } else {
                    //Remove the old owner and add the new owner
                    String oldOwner = old_professors[i].getPlayerID();

                    //...given that the owner has actually changed
                    if(!newOwner.equals(oldOwner)) {
                        Board newB = playerBoardMap.get(newOwner);
                        Board oldB = playerBoardMap.get(oldOwner);

                        newB.addProfessor(i);
                        oldB.removeProfessor(i);
                    }
                }
            }
        }
    }
}
