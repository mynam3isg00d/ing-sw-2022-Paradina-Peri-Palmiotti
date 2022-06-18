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
