package Controller;

import Events.ChooseWizardEvent;
import Events.MoveStudentToIslandEvent;
import Events.PlayAssistantEvent;
import Exceptions.IllegalAssistantException;
import Exceptions.NoSuchStudentsException;
import Exceptions.NotYourTurnException;
import Exceptions.WizardAlreadyChosenException;
import Model.*;
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
        CloudController cc = game.getCloudController();
        CloudWrapper cm = cc.getCloudModel();
        assertEquals(0, cm.peekAtCloud(0).size());
        assertEquals(0, cm.peekAtCloud(1).size());
        assertEquals(0, cm.peekAtCloud(2).size());

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

    public Game doSetupPhase() {
        //init a game with 3 players
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("id1", "1", 0);
        Player p2 = new Player("id2", "2", 1);
        Player p3 = new Player("id3", "3", 2);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        Game game = new Game(players);

        ChooseWizardEvent wizardEvent1 = new ChooseWizardEvent();
        wizardEvent1.setPlayerId("id1");
        wizardEvent1.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent1);
        });

        //creates an event: p2 wants to choose wizard 3
        ChooseWizardEvent wizardEvent3 = new ChooseWizardEvent();
        wizardEvent3.setPlayerId("id2");
        wizardEvent3.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent3);
        });

        //creates an event: p3 wants to choose wizard 2
        ChooseWizardEvent wizardEvent4 = new ChooseWizardEvent();
        wizardEvent4.setPlayerId("id3");
        wizardEvent4.parseInput("2");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(wizardEvent4);
        });

        return game;
    }
    @Test
    public void planningPhase() {
        Game game = doSetupPhase();

        List<Player> initialPlayers = game.getPlayers();

        GameModel gameModel = game.getGameModel();
        CloudController cc = game.getCloudController();
        CloudWrapper cm = cc.getCloudModel();

        //checks the game has progressed to planning phase and the clouds have been filled
        assertEquals(Phase.PLANNING , gameModel.getGamePhase());
        assertEquals(4, cm.peekAtCloud(0).size());
        assertEquals(4, cm.peekAtCloud(1).size());
        assertEquals(4, cm.peekAtCloud(2).size());


        //p1 wants to play the 3rd assistant in his hand
        PlayAssistantEvent ev1 = new PlayAssistantEvent();
        ev1.setPlayerId("id1");
        ev1.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev1);
        });


        //p2 wants to play the 4th assistant in his hand (the same that p1 has played)
        PlayAssistantEvent ev2 = new PlayAssistantEvent();
        ev2.setPlayerId("id2");
        ev2.parseInput("3");

        //should signal the assistant has already been played
        assertThrows(IllegalAssistantException.class, () -> {
            game.handleEvent(ev2);
        });


        //p2 wants to play the 1st assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev3 = new PlayAssistantEvent();
        ev3.setPlayerId("id2");
        ev3.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev3);
        });

        //p3 wants to play the 10th assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev4 = new PlayAssistantEvent();
        ev4.setPlayerId("id3");
        ev4.parseInput("9");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev4);
        });

        //checks the right assistants have been played
        //ie: order number of assistant in play is the selected index +1
        assertEquals(3+1 , initialPlayers.get(0).getAssistantInPlay().getOrderNumber());
        assertEquals(0+1 , initialPlayers.get(1).getAssistantInPlay().getOrderNumber());
        assertEquals(9+1 , initialPlayers.get(2).getAssistantInPlay().getOrderNumber());

        //all players have played an assistant:
        //- the player order should have been updated to p2 -> p1 -> p3
        //- we should be in action phase
        //- p2 should be the next player to play
        List<Player> newOrder = game.getPlayers();
        for (Player p : newOrder) {
            System.out.println(p.getPlayerID());
        }
        assertEquals("id2" , newOrder.get(0).getPlayerID());
        assertEquals("id1" , newOrder.get(1).getPlayerID());
        assertEquals("id3" , newOrder.get(2).getPlayerID());

        assertEquals(Phase.ACTION_STUDENTS , gameModel.getGamePhase());
        assertEquals("id2", gameModel.getCurrentPlayer().getPlayerID());
    }

    public Game doPlanningPhase() {
        Game game = doSetupPhase();

        List<Player> initialPlayers = game.getPlayers();


        //p1 wants to play the 3rd assistant in his hand
        PlayAssistantEvent ev1 = new PlayAssistantEvent();
        ev1.setPlayerId("id1");
        ev1.parseInput("3");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev1);
        });


        //p2 wants to play the 1st assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev3 = new PlayAssistantEvent();
        ev3.setPlayerId("id2");
        ev3.parseInput("0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev3);
        });

        //p3 wants to play the 10th assistant in his hand (no one has played that one yet)
        PlayAssistantEvent ev4 = new PlayAssistantEvent();
        ev4.setPlayerId("id3");
        ev4.parseInput("9");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev4);
        });

        //all players have played an assistant:
        //- the player order should have been updated to p2 -> p1 -> p3
        //- we should be in action phase
        //- p2 should be the next player to play

        return game;
    }

    @Test
    public void studentsToIsland() {
        Game game = doPlanningPhase();

        GameModel gameModel = game.getGameModel();
        IslandController ic = game.getIslandController();
        IslandsWrapper im = ic.getIslandModel();

        //after the planning phase has finished we should be in the action phase (players move students)
        assertEquals(Phase.ACTION_STUDENTS , gameModel.getGamePhase());

        //-------------------------------------------
        //p2 wants to move one student (the one in his first slot) to island 0
        MoveStudentToIslandEvent ev1 = new MoveStudentToIslandEvent();
        ev1.setPlayerId("id2");
        ev1.parseInput("0 + 0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev1);
        });
        //on island 0 there should be 1 student atm (since it is initialized with no students)
        assertEquals(1, countStudents(im.getStudents(0)));
        //game model has registered one student-move for the current turn
        //p2 is still the current player
        //ACTION_STUDENTS is still the game phase
        assertEquals(1, gameModel.getNumStudentsMoved());
        assertEquals("id2", gameModel.getCurrentPlayer().getPlayerID());
        assertEquals(Phase.ACTION_STUDENTS, gameModel.getGamePhase());
        assertEquals(1, gameModel.getRoundCount());

        //-----------------------------------------

        //p2 wants to move one student (the one in his first slot) to island 0
        MoveStudentToIslandEvent ev2 = new MoveStudentToIslandEvent();
        ev2.setPlayerId("id2");
        ev2.parseInput("0 + 0");

        //should signal the assistant has already been played
        assertThrows(NoSuchStudentsException.class, () -> {
            game.handleEvent(ev2);
        });

        //nothing changed
        assertEquals(1, countStudents(im.getStudents(0)));
        assertEquals(1, gameModel.getNumStudentsMoved());
        assertEquals("id2", gameModel.getCurrentPlayer().getPlayerID());
        assertEquals(Phase.ACTION_STUDENTS, gameModel.getGamePhase());
        assertEquals(1, gameModel.getRoundCount());

        //----------------------------------------------

        //p2 wants to move one student (the one in his second slot) to island 0
        MoveStudentToIslandEvent ev3 = new MoveStudentToIslandEvent();
        ev3.setPlayerId("id2");
        ev3.parseInput("1 + 0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev3);
        });
        //on island 0 there should be 2 students atm (since it is initialized with no students)
        assertEquals(2, countStudents(im.getStudents(0)));
        //game model has registered one student-move for the current turn
        //p2 is still the current player
        //ACTION_STUDENTS is still the game phase
        assertEquals(2, gameModel.getNumStudentsMoved());
        assertEquals("id2", gameModel.getCurrentPlayer().getPlayerID());
        assertEquals(Phase.ACTION_STUDENTS, gameModel.getGamePhase());

        //-----------------------------------------------------------

        //p1 wants to move one student (the one in his second slot) to island 0
        MoveStudentToIslandEvent ev4 = new MoveStudentToIslandEvent();
        ev4.setPlayerId("id1");
        ev4.parseInput("1 + 0");

        //should signal the turn is wrong
        assertThrows(NotYourTurnException.class, () -> {
            game.handleEvent(ev4);
        });

        //nothing changed
        assertEquals(2, countStudents(im.getStudents(0)));
        assertEquals(2, gameModel.getNumStudentsMoved());
        assertEquals("id2", gameModel.getCurrentPlayer().getPlayerID());
        assertEquals(Phase.ACTION_STUDENTS, gameModel.getGamePhase());

        //------------------------------------------------------------

        //p2 wants to move one student (the one in his third slot) to island 0
        MoveStudentToIslandEvent ev5 = new MoveStudentToIslandEvent();
        ev5.setPlayerId("id2");
        ev5.parseInput("2 + 0");

        //the event is processed correctly and no exception is thrown
        assertDoesNotThrow(() -> {
            game.handleEvent(ev5);
        });

        //turn info in gameModel should have been reset
        //game phase should still be ACTION_STUDENTS
        //p1 should now be in turn
        assertEquals(0, gameModel.getNumStudentsMoved());
        assertFalse(gameModel.hasMotherNatureMoved());
        assertFalse(gameModel.isCloudChosen());
        assertEquals(Phase.ACTION_STUDENTS, gameModel.getGamePhase());
        assertEquals("id1", gameModel.getCurrentPlayer().getPlayerID());
    }

    /**
     * Counts the number of students in an array
     * @param s
     * @return
     */
    private int countStudents(int[] s) {
        return s[0] + s[1] + s[2] + s[3] +s[4];
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