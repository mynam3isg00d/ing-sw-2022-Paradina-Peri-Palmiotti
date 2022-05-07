package Controller;

import Events.ChooseWizardEvent;
import Exceptions.WizardAlreadyChosenException;
import Model.GameModel;
import Model.Phase;
import Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void construtor() {
        //init a game with 3 players
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("id1", "1", 0);
        Player p2 = new Player("id2", "2", 1);
        Player p3 = new Player("id3", "3", 2);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(players);
        List<Player> actual = game.getPlayers();

        //checks if the players provided have been added to the list in game
        assertTrue(actual.contains(p1));
        assertTrue(actual.contains(p2));
        assertTrue(actual.contains(p3));
    }

    @Test
    public void setupPhase() {
        //init a game with 3 players
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("id1", "1", 0);
        Player p2 = new Player("id2", "2", 1);
        Player p3 = new Player("id3", "3", 2);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(players);
        GameModel gameModel = game.getGameModel();

        //---------------checking the game has been initialized correctly


        assertEquals(Phase.SETUP, gameModel.getGamePhase());
        //---------------


        //creates an event: p1 wants to choose wizard 0
        ChooseWizardEvent wizardEvent1 = new ChooseWizardEvent();
        wizardEvent1.setPlayerId("id1");
        wizardEvent1.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent1);
        });

        //the chosen wizard have been added to the correct player hand
        assertNotNull(players.get(0).getHand());
        assertEquals( 0, players.get(0).getHand().getWizardID() );
        //game is still in setup
        assertEquals(Phase.SETUP, gameModel.getGamePhase());

        //checks if the turn player has changed to p2 as it should have
        assertTrue(gameModel.getCurrentPlayer().equals(p2));

        //creates an event: p2 wants to choose wizard 0
        ChooseWizardEvent wizardEvent2 = new ChooseWizardEvent();
        wizardEvent2.setPlayerId("id2");
        wizardEvent2.parseInput("0");

        //should signal the wizard has already been chosen
        assertThrows(WizardAlreadyChosenException.class, () -> {
            game.handleEvent(wizardEvent2);
        });

        //checks everything is still unchanged:
        assertTrue(gameModel.getCurrentPlayer().equals(p2));
        assertNull(p2.getHand());
        //game is still in setup
        assertEquals(Phase.SETUP, gameModel.getGamePhase());

        //creates an event: p2 wants to choose wizard 3
        ChooseWizardEvent wizardEvent3 = new ChooseWizardEvent();
        wizardEvent3.setPlayerId("id2");
        wizardEvent3.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent3);
        });
        //game is still in setup
        assertEquals(Phase.SETUP, gameModel.getGamePhase());

        //creates an event: p3 wants to choose wizard 2
        ChooseWizardEvent wizardEvent4 = new ChooseWizardEvent();
        wizardEvent4.setPlayerId("id3");
        wizardEvent4.parseInput("2");

        System.out.println("DEBUG: current player: " + gameModel.getCurrentPlayer().getName());

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent4);
        });


        //now all players should have chosen a wizard deck ....
        //p1 -> wizard 0
        //p2 -> wizard 3
        //p3 -> wizard 2
        assertEquals(0, p1.getHand().getWizardID());
        assertEquals(3, p2.getHand().getWizardID());
        assertEquals(2, p3.getHand().getWizardID());
        assertNotNull(p1.getHand().getHand());
        assertNotNull(p2.getHand().getHand());
        assertNotNull(p3.getHand().getHand());

        assertEquals(Phase.PLANNING, gameModel.getGamePhase()); //... and the setup phase should have ended
        assertEquals(1, gameModel.getRoundCount()); //round 1 has started

        System.out.println("DEBUG: current player: " + gameModel.getCurrentPlayer().getName());
        assertEquals(p1, gameModel.getCurrentPlayer()); //p1 is the current player
    }

    private List<Player> getPlayerList(int n) {
        List<Player> ret = new ArrayList<>();
        String name = "a";
        for(int i=0; i<n; i++) {
            Player p = new Player(name, i);
            p.setPlayerID(name);
            ret.add(p);
            name = name + "a";
        }
        return ret;
    }
}