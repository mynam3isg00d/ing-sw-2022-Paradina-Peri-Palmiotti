//I hate this, i do not know if it works

package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.EmptySackException;
import Exceptions.FullElementException;
import Exceptions.InvalidPlayerInputException;
import Model.Student;
import Model.StudentCard;

import java.util.List;

public class ClerkEffect extends StudentsEffect {

    private IslandController ic;

    public ClerkEffect(String playerID) {
        super(playerID);
    }

    @Override
    public void init(ExpertGame g, int cardIndex) {
        //Connects the controller
        ic = g.getIslandController();
        sack = g.getSack();

        //Connects the model
        sc = (StudentCard)g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    @Override
    public void playEffect(List<Object> playerInput) throws InvalidPlayerInputException, Exception {

        //TODO: player input check

        //Expects
        //{studentIndex : int, islandIndex : int}
        int studentIndex = (Integer)playerInput.get(0);
        int islandIndex = (Integer)playerInput.get(1);

        //Get the student at said index
        Student s = sc.getStudent(studentIndex);

        //Move the student to the island
        ic.moveStudent(islandIndex, s);

        //Update model
        sc.addStudent(sack.draw(1).get(0));
    }
}
