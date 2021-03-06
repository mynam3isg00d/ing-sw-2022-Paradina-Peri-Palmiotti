package Controller.CharacterEffects;

import Controller.ExpertGame;
import Controller.IslandController;
import Exceptions.InvalidPlayerInputException;
import Model.Student;
import Model.StudentCard;

import java.util.List;

//Take 1 Student from this card and place it on an Island of your choice. Then, draw a new Student from the Bag and place it on this card
public class ClerkEffect extends StudentsEffect {

    private IslandController ic;

    public ClerkEffect(String playerID) {
        super(playerID);
    }

    /**
     * Initializes the attributes with the appropriate references
     * @param g reference to expertGame
     * @param cardIndex index of the Clerk character card
     */
    @Override
    public void init(ExpertGame g, int cardIndex) {
        //Connects the controller
        ic = g.getIslandController();
        sack = g.getSack();

        //Connects the model
        sc = (StudentCard)g.getCharacterController().getShopReference().getShop()[cardIndex];
    }

    /**
     * Moves the selected student to the selected island and updates the model.
     * @param playerInput the index of the student and of the island selected by the player
     * @throws InvalidPlayerInputException the input given by the player is invalid
     * @throws Exception something went wrong
     */
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
