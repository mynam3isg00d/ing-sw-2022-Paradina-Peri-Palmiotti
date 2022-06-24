package Model;

import com.google.gson.annotations.Expose;

/**
 * Each student has a colorId in order to make it easier to know where it should be placed on the Board
 */
public enum Student {
    YELLOW(0),
    BLUE(1),
    GREEN(2),
    RED(3),
    PINK(4);

    
    private int colorId;

    Student(int colorId){
        this.colorId=colorId;
    }

    public int getColorId() {
        return colorId;
    }

    public static Student idToStudent(int i) {
        switch (i) {
            case 0: return YELLOW;
            case 1: return BLUE;
            case 2: return GREEN;
            case 3: return RED;
            case 4: return PINK;
            default: return null;
        }
    }

    /**
     * Returns the first letter of the student's color. See below for further explanation
     * @return The character corresponding to the color of the student
     * Y = YELLOW
     * B = BLUE
     * G = GREEN
     * R = RED
     * P = PINK
     */
    public char getChar() {
        switch(colorId) {
            case 0: return 'Y';
            case 1: return 'B';
            case 2: return 'G';
            case 3: return 'R';
            case 4: return 'P';
        }
        return '-';
    }
}
