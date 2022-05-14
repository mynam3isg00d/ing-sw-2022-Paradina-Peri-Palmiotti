package Controller;

import Events.BuyPlayCharacterEvent;
import Events.MoveStudentToDiningEvent;
import Exceptions.*;
import Model.Board;
import Model.Phase;
import Model.Player;
import View.RemoteView;

import java.util.List;

public class ExpertGame extends Game {

    private CharacterController characterController;

    public ExpertGame(List<Player> players) {
        super(players);
        characterController = new CharacterController(this);
    }

    public void handleEvent(BuyPlayCharacterEvent event) throws NotYourTurnException, InvalidPlayerInputException, InsufficientCoinsException, InvalidMoveException, Exception {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not correct phase
        if (gameModel.getGamePhase().equals(Phase.SETUP) || gameModel.getGamePhase().equals(Phase.PLANNING))
            throw new InvalidMoveException("scusa, ma cosa fai vez, i personaggi li compri solo nella fase di azione. " +
                    "                       rileggiti le regole sarebbe meglio. davvero ma come ti è venuto di fare " +
                    "                       sta cosa adesso. che cosa pensavi di ottenere, pensavi di fare il furbetto" +
                    "                       'haha adesso compro la carta quando non è la fase giusta hihi' beh fratello" +
                    "                       come puoi vedere non sta ridendo nessuno ci hai fotto solo perdere tempo. " +
                    "                       pensa quanto tempo di trasmissione è stato sprecato per inviare questa idiozia" +
                    "                       pensa agli avanzamenti tecnologici che sono avvenuti negli anni per permetterti" +
                    "                       di fare una mossa del genere, praticamente sputando in faccia a tutte le persone" +
                    "                       che con il loro duro lavoro ci hanno portato la capacità di fare cose straordinarie" +
                    "                       che tu non stai minimamente rispettando. vergognati. imbecille.");
        characterController.buyCard(event.getCardID(), gameModel.getCurrentPlayer().getPlayerID(), event.getParameters());
    }

    public void handleEvent(MoveStudentToDiningEvent event) throws NoSuchStudentsException, NotYourTurnException, InvalidMoveException, FullTableException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //already moved three students
        if (gameModel.getNumStudentsMoved() >= 3) throw new InvalidMoveException("You can't move any more students");

        boardsController.moveFromEntranceToDining(event.getPlayerId(), event.getStudentIndex());

        //add coins!
        Board b = boardsController.getBoard(event.getPlayerId());
        if (b.getDinings()[event.getStudentIndex()] % 3 == 0) characterController.giveCoins(event.getEventId(), 1);

        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved 3 students the ACTION_STUDENTS phase has ended
        if (gameModel.getNumStudentsMoved() == 3) {
            gameModel.setGamePhase(Phase.ACTION_MOTHERNATURE);
        }
    }

    public CharacterController getCharacterController() {
        return characterController;
    }

    public void addObserversToModelComponents(RemoteView rv) {
        super.addObserversToModelComponents(rv);
        characterController.addObserverToModel(rv);
    }

    //--------
    //Testing functions
    //--------


    public void setCharacterController(CharacterController characterController) {
        this.characterController = characterController;
    }
}
