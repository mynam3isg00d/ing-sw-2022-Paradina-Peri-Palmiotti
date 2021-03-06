package Controller;

import Exceptions.InvalidNumberOfPlayersException;
import Model.IslandsWrapper;
import Model.Player;
import Model.Sack;
import Model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandControllerTest {

    @Test
    void initIslands() {
        IslandController ic = new IslandController();
        IslandsWrapper im = ic.getIslandModel();

        ic.initIslands();

        assertEquals(im.getIslandLength(), ic.getIslandsQuantity());

        assertEquals(0, countStudents(im.getStudents(0)));
        assertEquals(1, countStudents(im.getStudents(1)));
        assertEquals(1, countStudents(im.getStudents(2)));
        assertEquals(1, countStudents(im.getStudents(3)));
        assertEquals(1, countStudents(im.getStudents(4)));
        assertEquals(1, countStudents(im.getStudents(5)));
        assertEquals(0, countStudents(im.getStudents(6)));
        assertEquals(1, countStudents(im.getStudents(7)));
        assertEquals(1, countStudents(im.getStudents(8)));
        assertEquals(1, countStudents(im.getStudents(9)));
        assertEquals(1, countStudents(im.getStudents(10)));
        assertEquals(1, countStudents(im.getStudents(11)));
    }

    public int countStudents(int[] s) {
        return s[0] + s[1] + s[2] + s[3] + s[4];
    }
    @Test
    void moveMotherOnNullIsland() throws InvalidNumberOfPlayersException {
        //init
        IslandController islandController = new IslandController();

        IslandsWrapper islandModel = new IslandsWrapper();
        islandController.connectIslandModel(islandModel);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", "Jay-Z", 1));
        players.add(new Player("2", "Achille Frigeri", 2));
        BoardsController boardsController = new BoardsController(players, new Sack(120));
        islandController.connectBoards(boardsController);

        //adds some students on the island 1
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.RED);
        toAdd.add(Student.PINK);
        toAdd.add(Student.PINK);
        islandModel.addStudents(1, toAdd);

        //sets professors in boardController. The resulting influence is: 3 for team 1 - 0 for team 2
        boardsController.setProfessor("Y", players.get(0));
        boardsController.setProfessor("B", players.get(1));
        boardsController.setProfessor("G", players.get(1));
        boardsController.setProfessor("P", players.get(0));

        try {
            //moves from island 0 to island 1
            islandController.moveMother(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        //tests if the influence has actually changed
        assertEquals(1, islandModel.getInfluence(1));
    }

    @Test
    void moveMotherOnIslandToMerge() throws InvalidNumberOfPlayersException {
        //init
        IslandController islandController = new IslandController();

        IslandsWrapper islandModel = new IslandsWrapper();
        islandController.connectIslandModel(islandModel);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", "Jay-Z", 1));
        players.add(new Player("2", "Achille Frigeri", 2));
        BoardsController boardsController = new BoardsController(players, new Sack(120));
        islandController.connectBoards(boardsController);

        //adds some students on the island 1
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.RED);
        toAdd.add(Student.PINK);
        toAdd.add(Student.PINK);
        islandModel.addStudents(1, toAdd);

        //sets professors in boardController. The resulting influence is: 3 for team 1 - 0 for team 2
        boardsController.setProfessor("Y", players.get(0));
        boardsController.setProfessor("B", players.get(1));
        boardsController.setProfessor("G", players.get(1));
        boardsController.setProfessor("P", players.get(0));

        //puts influence 1 on island 2 in order to test the merge of the islands
        islandModel.setInfluence(2, 1);

        try {
            //moves from island 0 to island 1
            islandController.moveMother(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println(islandModel.getIsland(1));

        //tests if the influence has actually changed and the islands have been merged
        assertEquals(1, islandModel.getInfluence(1));
        assertEquals(2, islandModel.getIslandDimension(1));
    }

    @Test
    void moveMotherOnIslandToMergeEdge() throws InvalidNumberOfPlayersException {
        //init
        IslandController islandController = new IslandController();

        IslandsWrapper islandModel = new IslandsWrapper();

        //puts mother nature on island 9
        islandModel.moveMotherNature(9);

        islandController.connectIslandModel(islandModel);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", "Jay-Z", 1));
        players.add(new Player("2", "Achille Frigeri", 2));
        BoardsController boardsController = new BoardsController(players, new Sack(120));
        islandController.connectBoards(boardsController);

        //adds some students on the island 11
        List<Student> toAdd = new ArrayList<>();
        toAdd.add(Student.YELLOW);
        toAdd.add(Student.RED);
        toAdd.add(Student.PINK);
        toAdd.add(Student.PINK);
        islandModel.addStudents(11, toAdd);

        //sets professors in boardController. The resulting influence is: 3 for team 1 - 0 for team 2
        boardsController.setProfessor("Y", players.get(0));
        boardsController.setProfessor("B", players.get(1));
        boardsController.setProfessor("G", players.get(1));
        boardsController.setProfessor("P", players.get(0));

        //puts influence 1 on island 11 in order to test the merge of the islands
        islandModel.setInfluence(0, 1);

        try {
            //moves from island 9 to island 11
            islandController.moveMother(2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println(islandModel.getIsland(10));

        //tests if the influence has actually changed and the islands have been merged
        assertEquals(1, islandModel.getInfluence(10));
        assertEquals(2, islandModel.getIslandDimension(10));
    }

    @Test
    void moveStudent() {
        IslandController ic = new IslandController();
        IslandsWrapper iw = new IslandsWrapper();
        ic.connectIslandModel(iw);

        ic.moveStudent(3, Student.YELLOW);

        int[] expected = new int[5];
        expected[0] = 1;

        assertArrayEquals( expected, iw.getStudents(3));


    }

    @Test
    void noEntry() {
        IslandController ic = new IslandController();
        assertDoesNotThrow(() -> {
            ic.setNoEntry(3);
        });

        assertTrue(ic.getIslandModel().isNoEntry(3));

        assertDoesNotThrow(() -> {
            ic.removeNoEntry(3);
        });

        assertFalse(ic.getIslandModel().isNoEntry(3));
    }
}