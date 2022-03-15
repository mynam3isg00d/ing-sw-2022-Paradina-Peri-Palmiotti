package Model;

//each student has a colorId in order to make it easier to know where it should be placed on the Board
public enum Student {
    RED(0),
    GREEN(1),
    YELLOW(2),
    BLUE(3),
    PINK(4);

    private int colorId;

    Student(int colorId){
        this.colorId=colorId;
    }

    public int getColorId() {
        return colorId;
    }
}
