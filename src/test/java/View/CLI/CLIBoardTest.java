package View.CLI;

import Exceptions.EmptyElementException;
import Exceptions.EmptySackException;
import Exceptions.FullEntranceException;
import Exceptions.FullTableException;
import Model.Board;
import Model.Sack;
import Model.Student;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CLIBoardTest {

    @Test
    void displayTest() {

        Board b = new Board(8, 7);
        CLIBoard clib = new CLIBoard(b);

        AnsiConsole.systemInstall();
        System.out.println("ESC[1;31m");
        //clib.displayLines();

        /*
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
        */
    }
}