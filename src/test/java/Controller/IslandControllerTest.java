package Controller;

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
    void moveMotherOnNullIsland() {
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
    void moveMotherOnIslandToMerge() {
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
    void moveStudent() {
        IslandController ic = new IslandController();
        IslandsWrapper iw = new IslandsWrapper();
        ic.connectIslandModel(iw);

        ic.moveStudent(3, Student.YELLOW);

        int[] expected = new int[5];
        expected[0] = 1;

        assertArrayEquals( expected, iw.getStudents(3));


    }
}