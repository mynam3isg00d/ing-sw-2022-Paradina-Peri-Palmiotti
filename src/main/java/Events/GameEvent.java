package Events;

import java.util.List;

public abstract class GameEvent {
    protected String playerId;

    //a 4-digits unique id used to cast events to the right sub-class
    protected String eventId;

    public String getEventId() { return eventId; }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public abstract void parseInput(String input);

}


/*

    EVENT HANDLING: TRANSFER THIS TO THE SINGLE EVENT CLASSES

    public void handleEvent(GameEvent gameEvent) {
        switch (gameEvent.getMove()){
            case A:
                //Check if the assistant played is in the player's hand.
                //Check if the assistant is legal, i.e. has not yet been played by someone else

                //Update model
                break;
            case B:
                //Check if arguments are legal.
                //Check if studentID is available to be moved
                //Move student to dining room

                //Update model moving student to dining room
                break;
            case C:
                //Check if arguments are legal.
                //Check if studentID is available to be moved

                //Update model moving student to island
                break;
            case D:
                //Check if number of steps is legal

                //Update model by relocating mother nature
                break;
            case E:
                //Check if cloudID is legal
                //Check if cloud is not empty

                //Update model by adding the students that are on the cloud to the entrance of the player
                break;
            case F:
                //handle event
                break;
            case G:
                //Check if wizard is available

                //Update model by assigning wizard's deck to player
                break;

        }
    }
     */