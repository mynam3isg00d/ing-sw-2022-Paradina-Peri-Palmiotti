package Model;

//each student has a colorId in order to make it easier to know where it should be placed on the Board
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


}
