package Controller;

import Exceptions.EmptyElementException;
import Exceptions.FullElementException;
import Exceptions.FullTableException;
import Exceptions.NoSuchStudentsException;
import Model.Board;
import Model.Player;
import Model.Sack;
import Model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class BoardsControllerTest {

    @Test
    public void createTwoBoardsTest() {
        List<Player> pl = getPlayerList(2);
        BoardsController bc = new BoardsController(pl, new Sack(120));

        for(Player p : pl) {
            String pid = p.getPlayerID();
            Board b = bc.getBoard(pid);

            Student[] entrance = b.getEntrance();
            int[] dinings = b.getDinings();
            int towersNum = b.getTowersNum();
            boolean[] professors = b.getProfessors();

            assertTrue(entrance.length == 7);
            assertTrue(dinings.length == 5);
            assertTrue(towersNum == 8);
            assertTrue(professors.length == 5);

            for(int i=0; i < entrance.length; i++) {
                assertTrue(entrance[i] instanceof Student);
            }

            for(int i=0; i < dinings.length; i++) {
                assertTrue(dinings[i] == 0);
            }

            for(int i=0; i < professors.length; i++) {
                assertFalse(professors[i]);
            }
        }
    }

    @Test
    public void createThreeBoardsTest() {
        List<Player> pl = getPlayerList(3);
        BoardsController bc = new BoardsController(pl, new Sack(120));

        for(Player p : pl) {
            String pid = p.getPlayerID();
            Board b = bc.getBoard(pid);

            Student[] entrance = b.getEntrance();
            int[] dinings = b.getDinings();
            int towersNum = b.getTowersNum();
            boolean[] professors = b.getProfessors();

            assertTrue(entrance.length == 9);
            assertTrue(dinings.length == 5);
            assertTrue(towersNum == 6);
            assertTrue(professors.length == 5);

            for(int i=0; i < entrance.length; i++) {
                assertTrue(entrance[i] instanceof Student);
            }

            for(int i=0; i < dinings.length; i++) {
                assertTrue(dinings[i] == 0);
            }

            for(int i=0; i < professors.length; i++) {
                assertFalse(professors[i]);
            }
        }
    }

    @Test
    public void createFourBoardsTest() {
        List<Player> pl = getPlayerList(4);
        BoardsController bc = new BoardsController(pl, new Sack(120));

        for(Player p : pl) {
            String pid = p.getPlayerID();
            Board b = bc.getBoard(pid);

            Student[] entrance = b.getEntrance();
            int[] dinings = b.getDinings();
            int towersNum = b.getTowersNum();
            boolean[] professors = b.getProfessors();

            assertTrue(entrance.length == 7);
            assertTrue(dinings.length == 5);
            assertTrue(towersNum == 8 || towersNum == 0);
            assertTrue(professors.length == 5);

            for(int i=0; i < entrance.length; i++) {
                assertTrue(entrance[i] instanceof Student);
            }

            for(int i=0; i < dinings.length; i++) {
                assertTrue(dinings[i] == 0);
            }

            for(int i=0; i < professors.length; i++) {
                assertFalse(professors[i]);
            }
        }
    }

    @Test
    void someRoundTesting() throws NoSuchStudentsException, FullElementException, EmptyElementException {
        List<Player> pl = getPlayerList(2);
        BoardsController bc = new BoardsController(pl, new Sack(120));

        //MoveFromEntranceToDining test
        bc.moveFromEntranceToDining(pl.get(0).getPlayerID(), 3);
        bc.moveFromEntranceToDining(pl.get(0).getPlayerID(), 5);
        bc.moveFromEntranceToDining(pl.get(0).getPlayerID(), 1);

        bc.moveFromEntranceToDining(pl.get(1).getPlayerID(), 4);

        //first update professor test (branch with no prev owner)
        bc.updateProfessors();

        //remove and add tower test
        bc.removeTowers(pl.get(0).getPlayerID(), 2);
        bc.addTowers(pl.get(0).getPlayerID(), 2);

        //add to entrance and add to dining tests
        bc.addToEntrance(pl.get(0).getPlayerID(), Arrays.asList(Student.RED, Student.RED, Student.RED));
        bc.addToDining(pl.get(1).getPlayerID(), Student.RED);
        bc.addToDining(pl.get(1).getPlayerID(), Student.RED);
        bc.addToDining(pl.get(1).getPlayerID(), Student.RED);

        //second update professor test (branch with prev owner, hopefully)
        bc.updateProfessors();
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