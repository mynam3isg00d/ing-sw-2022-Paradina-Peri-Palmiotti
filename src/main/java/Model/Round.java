/*package Model;

public class Round {
    private int roundNumber;
    private List<Player> playerOrder;
    private List<Assistant> playedAssistants;

    private Sack sack;
    private IslandController islandController;
    private CloudController cloudController;

    public Round(Game g) {
        //when Round is built, playerOrder is yet to be ordered. Players are ordered
        //as they are at the beginning of the game. They will be ordered
        //correctly in planningPhase.
        playerOrder = new ArrayList<Player>(g.getPlayers());
        playedAssistants = new ArrayList<Assistant>();

        sack = g.getSack();
        islandController = g.getIslandController();
        cloudController = g.getCloudController();
    }

    public void planningPhase() {
        //draw 3 students from the bag for each Cloud
        cloudController.fillClouds(sack);

        //Players choose 1 Assistant card, starting from the
        //first player and proceeding in a clockwise order
        for(Player p : playerOrder) {
            //playAssistant returns the played assistant.
            //playAssistant requires the list of unplayable cards.
            Assistant played = p.playAssistant(playedAssistants);
            playedAssistants.add(played);
            //TODO: order playerOrder
        }

        //Assuming the following players are in order

    }

    public void actionPhase() {
        //1.Move 3 Students from the board to either your Dining Room or an Island
        //2.Move mother nature to an island, check if island is conquered, unify islands
        //  if allowed.
        //3.Choose a cloud and pick the students on it
        for(Player p : playerOrder) {
            p.moveToDining();

            //Show available island information!
            islandController.showIslands();

            //moveToIsland returns the list of students that
            //p wants to move to an Island

            //toMove: INTEGER is Island Index, STUDENT is the student to move;
            HashMap<Integer, Student> toMove = new HashMap<Integer, Student>();
            toMove = p.moveToIsland();

            //Forward toMove information to IslandController somehow
            //IslandController writer, you decide how to implement


            //Move motherNature by a number of steps that is forwarded from the view

            //NOTE: check for max movement granted by assistant and other checks
            int steps = p.getMotherNatureSteps();
            islandController.moveMother(steps);


            //view tells which cloud to pick from
            Cloud cloudToPickFrom = //view give me a cloud
            ArrayList<Student> moveableStudents = cloudToPickFrom.empty();

            //interaction with view: choose how to move moveableStudents
        }

        //Move Mother Nature to an Island
    }
}*/
