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

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.*;

class CLIBoardTest {

    @Test
    void displayTest() {

        Board b = new Board(8, 7);
        CLIBoard clib = new CLIBoard(b);

        AnsiConsole.systemInstall();
        System.out.println(ansi().eraseScreen());
        clib.displayLines(0, 0);
        try {
            List<Student> s = new Sack(120).draw(7);
            b.addToEntrance(s);
            b.removeTower(3);
            b.addProfessor(4);
            b.addToDining(Student.RED);
        } catch (FullEntranceException | EmptyElementException | FullTableException e) {
            e.printStackTrace();
        }
        clib.updateBoard(b);

        for(int i=0; i<2; i++) {
            for(int j=0; j<4; j++) {
                clib.displayLines(i*clib.getX(), j*clib.getY());
            }
        }
    }
}