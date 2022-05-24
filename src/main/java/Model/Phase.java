package Model;

public enum Phase {
    SETUP(0),

    PLANNING(1),

    ACTION_STUDENTS(2),

    ACTION_MOTHERNATURE(3),

    ACTION_CLOUDS(4),

    END(5);

    private int order;
    Phase(int order) {
        this.order = order;
    }
}
