package Model;

import com.google.gson.annotations.Expose;

//each student has a colorId in order to make it easier to know where it should be placed on the Board
public enum Student {
    YELLOW(0),
    BLUE(1),
    GREEN(2),
    RED(3),
    PINK(4);

    @Expose
    private int colorId;

    Student(int colorId){
        this.colorId=colorId;
    }

    public int getColorId() {
        return colorId;
    }

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
