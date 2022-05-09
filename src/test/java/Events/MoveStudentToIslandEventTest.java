package Events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentToIslandEventTest {

    @Test
    void parseInput() {
        MoveStudentToIslandEvent e = new MoveStudentToIslandEvent();

        e.parseInput("3 + 4");

        assertEquals(3, e.getStudentBoardIndex());
        assertEquals(4, e.getIslandID());
    }
}