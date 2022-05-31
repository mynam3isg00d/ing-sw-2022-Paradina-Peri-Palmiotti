package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;
import Model.Student;
import Model.StudentCard;

import java.util.List;

public class ClerkEffect extends StudentsEffect {

    private IslandController ic;

    @Override
    public String explainEffect() {
        return "Take 1 Student from this card and place it on an Island of your choice. Then, draw a new Student from the Bag and place it on this card.";
    }

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
    public void playEffect(List<String> playerInput) throws InvalidPlayerInputException, Exception {

        //Expects:
        //{studentIndex : int, islandIndex : int}
        if (playerInput.size() != 2) throw new InvalidPlayerInputException();

        int studentIndex = Integer.parseInt(playerInput.get(0));
        if (studentIndex < 0 || studentIndex > sc.getMAX_STUDENTS() - 1) throw new InvalidPlayerInputException();
        int islandIndex = Integer.parseInt(playerInput.get(1));
        if (islandIndex < 0 || islandIndex > ic.getIslandsQuantity() - 1) throw new InvalidPlayerInputException();

        //Get the student at said index
        Student s = sc.getStudent(studentIndex);

        //Move the student to the island
        ic.moveStudent(islandIndex, s);

        //Update model
        sc.addStudent(sack.draw(1).get(0));
    }
}
