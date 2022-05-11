package View.CLI;

import Exceptions.EmptyElementException;
import Exceptions.EmptySackException;
import Exceptions.FullEntranceException;
import Exceptions.FullTableException;
import Model.Board;
import Model.Sack;
import Model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CLIBoardTest {

    @Test
    void displayTest() {

        Board b = new Board(8, 7);
        CLIBoard clib = new CLIBoard(b);

        clib.displayLines();
        try {
            b.addToEntrance(new Sack(120).draw(7));
            b.removeTower(3);
            b.addProfessor(4);
            b.addToDining(Student.RED);
        } catch (FullEntranceException | EmptyElementException | FullTableException e) {
            e.printStackTrace();
        }
        clib.updateBoard(b);

        clib.displayLines();
    }

    @Test
    void fourboards() {

        Board[] boards = new Board[4];
        Sack s = new Sack(120);
        for(int i=0; i<boards.length; i++) {
            boards[i] = new Board(8, 7);
            try {
                boards[i].addToEntrance(s.draw(7));
            } catch (FullEntranceException | EmptySackException e) {
                e.printStackTrace();
            }
        }
        CLIBoard[] CLIboards = new CLIBoard[4];
        for(int i=0; i< CLIboards.length; i++) CLIboards[i] = new CLIBoard(boards[i]);

        //Drawing
        for(int i=0; i<8; i++) {
            for(int j=0; j<4; j++) {
                System.out.print(CLIboards[j].getLines().get(i) + "   ");
            }
            System.out.print("\n");
        }
    }
}