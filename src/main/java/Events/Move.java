package Events;

public enum Move {
    A("Play an assistant card"),
    B("Move a student to dining room"),
    C("Move a student to island"),
    D("Move mother nature"),
    E("Pick students from cloud"),
    F("Buy and play character"),
    G("Choose a wizard");

    private String moveDescription;

    public String getMoveDescription() {
        return moveDescription;
    }

    Move(String moveDescription) {
        this.moveDescription = moveDescription;
    }
}
