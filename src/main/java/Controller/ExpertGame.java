package Controller;

import Controller.CharacterEffects.Strategies.DefaultInfluenceStrategy;
import Events.*;
import Exceptions.*;
import Model.Board;
import Model.Phase;
import Model.Player;
import Model.Student;
import View.RemoteView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

public class ExpertGame extends Game {

    private CharacterController characterController;

    public ExpertGame(List<Player> players) {
        super(players);
        characterController = new CharacterController(this);
    }


    @Override
    public void sendEntireModel() {
        super.sendEntireModel();
        characterController.getShopReference().sendShop();
    }

    public void handleEvent(BuyPlayCharacterEvent event) throws NotYourTurnException, InvalidPlayerInputException, InsufficientCoinsException, InvalidMoveException, Exception {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not correct phase
        if (gameModel.getGamePhase().equals(Phase.SETUP) || gameModel.getGamePhase().equals(Phase.PLANNING))
            throw new InvalidMoveException(String.format("scusa, ma cosa fai vez, i personaggi li compri solo nella fase di azione. rileggiti le regole sarebbe meglio. davvero ma come ti è venuto di fare sta cosa adesso. che cosa pensavi di ottenere, pensavi di fare il furbetto 'haha adesso compro la carta quando non è la fase giusta hihi' beh fratello come puoi vedere non sta ridendo nessuno ci hai fotto solo perdere tempo. pensa quanto tempo di trasmissione è stato sprecato per inviare questa idiozia pensa agli avanzamenti tecnologici che sono avvenuti negli anni per permetterti di fare una mossa del genere, praticamente sputando in faccia a tutte le persone che con il loro duro lavoro ci hanno portato la capacità di fare cose straordinarie che tu non stai minimamente rispettando. vergognati. imbecille."));
        characterController.buyCard(event.getCardID(), gameModel.getCurrentPlayer().getPlayerID(), event.getParameters());
    }

    public void handleEvent(MoveStudentToDiningEvent event) throws NoSuchStudentsException, NotYourTurnException, InvalidMoveException, FullTableException {
        //not your turn
        if (!gameModel.getCurrentPlayer().getPlayerID().equals(event.getPlayerId())) throw new NotYourTurnException();

        //not the right phase
        if (!gameModel.getGamePhase().equals(Phase.ACTION_STUDENTS)) throw new InvalidMoveException("You can't move any student now");

        //already moved three students
        if (gameModel.getNumStudentsMoved() >= gameModel.getSTUDENTS_PER_TURN()) throw new InvalidMoveException("You can't move any more students");

        int color = boardsController.getBoard(event.getPlayerId())
                        .getEntrance()[event.getStudentIndex()].getColorId();
        boardsController.moveFromEntranceToDining(event.getPlayerId(), event.getStudentIndex());
        boardsController.updateProfessors();

        //add coins!
        Board b = boardsController.getBoard(event.getPlayerId());
        if (b.getDinings()[color] % 3 == 0) characterController.giveCoins(event.getPlayerId(), 1);

        //add one student to the turn info
        gameModel.studentMoved();

        //if the player has moved STUDENTS_PER_TURN students the turn changes
        if (gameModel.getNumStudentsMoved() == gameModel.getSTUDENTS_PER_TURN()) {
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

    @Override
    protected void endTurn(String pid) {
        //Reset the default influence strategy
        islandController.setInfluenceStrategy(new DefaultInfluenceStrategy());

        super.endTurn(pid);
    }

    //--------
    //Testing functions
    //--------


    public void setCharacterController(CharacterController characterController) {
        this.characterController = characterController;
    }

    public void jsonToEvent(String json) throws Exception {
        Gson b = new GsonBuilder().serializeNulls().create();

        JsonObject messageAsJsonObject = b.fromJson(json, JsonObject.class);
        String code = messageAsJsonObject.get("eventId").getAsString();

        switch (code) {
            case "0000":
                handleEvent(b.fromJson(json, ChooseWizardEvent.class));
                break;
            case "0001":
                handleEvent(b.fromJson(json, PlayAssistantEvent.class));
                break;
            case "0002":
                handleEvent(b.fromJson(json, MoveStudentToDiningEvent.class));
                break;
            case "0003":
                handleEvent(b.fromJson(json, MoveStudentToIslandEvent.class));
                break;
            case "0004":
                handleEvent(b.fromJson(json, MoveMotherNatureEvent.class));
                break;
            case "0005":
                handleEvent(b.fromJson(json, PickStudentsFromCloudEvent.class));
                break;
            case "0006":
                handleEvent(b.fromJson(json, BuyPlayCharacterEvent.class));
                break;
            default:
                System.out.println("Error in ExpertGame.jsonToEvent: code not supported");
        }
    }
}
