package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {

    @Test
    void assistantConstructorTest() {
        Assistant[] array = new Assistant[10];
        for(int i=1; i<=10; i++) {
            array[i-1] = new Assistant(i);
        }
        for(int i=1; i<=10; i++) {
            assertEquals(i, array[i-1].getOrderNumber());
            assertEquals(((i - 1) / 2) + 1, array[i-1].getMotherNumber());
        }
    }
}