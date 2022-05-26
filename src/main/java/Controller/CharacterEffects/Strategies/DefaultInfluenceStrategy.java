package Controller.CharacterEffects.Strategies;

import Controller.BoardsController;
import Model.IslandsWrapper;
import Model.Player;
import Model.Sack;
import Model.Student;

import java.util.*;


public class DefaultInfluenceStrategy implements InfluenceStrategy{
    /**
     * Calculates the most influent team and returns its id
     * @param islandIndex The island in question
     * @param islandModel The reference to the IslandModel
     * @param boardsController The reference to the BoardsController
     * @return The most influent team if there is one, else -1 (if no further action is needed)
     */
    public int calcInfluence(int islandIndex, IslandsWrapper islandModel, BoardsController boardsController){
        //gets the students on the island, if there are no students on the island the situation remains unchanged
        int[] students = islandModel.getStudents(islandIndex);
        int[] noStudentsArray = new int[] {
                0,0,0,0,0
        };
        if (Arrays.equals(students, noStudentsArray)) {
            System.out.println("CONTROLLER SAYS: no students on the island. Nothing changes");
            return -1;
        }


        //every team influence will be stored in a hashmap like:
        //teamId -> influence
        HashMap<Integer, Integer> influences = new HashMap<>();

        //checks which teams COULD have some influence on the island (ie: the teams which have at least one professor) and puts them on the infuences map
        List<Student> colors = Sack.getColors();
        for (Student s : colors) {
            Player owner = boardsController.getProfessorOwner(s.getColorId());
            if (owner != null && !influences.containsKey(owner.getTeamID())) influences.put(owner.getTeamID(), 0);
        }

        //if no professor is assigned, no team can have influence>0 on the island
        if (influences.isEmpty()) {
            System.out.println("CONTROLLER SAYS: no professor is assigned. Nothing changes");
            return -1;
        }

        //the team who has one or more towers get an extra point of influence for every one of them
        Integer currentInfluence = islandModel.getInfluence(islandIndex);
        if ( currentInfluence != null) {
            influences.put(currentInfluence, islandModel.getIslandDimension(islandIndex));
        }


        for(int color = 0; color < 5; color++) {
            //studentsOnIsland is the number of [color] students already on the island
            int studentsOnIsland = students[color];

            Player professorOwner = boardsController.getProfessorOwner(color);
            if (professorOwner != null) {
                //playerKey is the player who controls the [color] professor
                Player playerKey = professorOwner;

                //influenceAdded is the influence value given by the control of the professor in consideration
                //puts oldInfluence + studentsOnIsland in the hashmap next to the relative team
                int oldInfluence = influences.get(playerKey.getTeamID());
                influences.put(playerKey.getTeamID(), oldInfluence+studentsOnIsland);
            }
        }

        //if no teams have influence -> return -1
        boolean hasSomeoneInfluence = false;
        for (Map.Entry<Integer, Integer> e : influences.entrySet()) {
            if (e.getValue() != 0) hasSomeoneInfluence = true;
        }
        if (!hasSomeoneInfluence) return -1;

        //empties the map in order to obtain a list of map entries sorted by influence
        List<Map.Entry<Integer, Integer>> topInfluences = new ArrayList<>();
        int influencesSize = influences.size();
        for (int j = 0; j < influencesSize; j++){
            int maxInfluence = 0;
            Map.Entry<Integer, Integer> bestEntry = null;

            for (Map.Entry<Integer, Integer> e : influences.entrySet()) {
                if (e.getValue() >= maxInfluence) {
                    maxInfluence = e.getValue();
                    bestEntry = e;
                }
            }

            topInfluences.add(bestEntry);
            System.out.println("CONTROLLER SAYS: added to topInfluences: " + bestEntry.getKey() + "   " + bestEntry.getValue());
            influences.remove(bestEntry.getKey());
        }

        int maximumInfluence = topInfluences.get(0).getValue();

        //only leaves the best scoring entries in the list
        topInfluences.removeIf(e -> e.getValue() != maximumInfluence);

        //handles ties and decides which is the new most influent team
        //TODO handle null pointers in currentInfluence
        int mostInfluentTeam;
        if (topInfluences.size() == 1) {
            mostInfluentTeam = topInfluences.get(0).getKey(); //the team with the maximum influence
        } else {
            boolean isOldInfluenceInTheList = false;
            for (Map.Entry<Integer, Integer> e : topInfluences) {
                if (e.getKey().equals(currentInfluence)) isOldInfluenceInTheList = true;
            }

            if (isOldInfluenceInTheList) {
                mostInfluentTeam = currentInfluence;   //in case of a tie between the team which currently has the tower and another team, the influence remains unchanged
            }
            else mostInfluentTeam = -1;     //signals that we get a tie between two teams which don't currently have influence on the island, so the situation should remain unchanged
        }

        System.out.println("The most influent team on Island " + islandIndex + " is team " + mostInfluentTeam);

        //if the influence has changed, returns the new most influent team
        //otherwise (influence hasn't changed) returns -1
        if (currentInfluence == null || mostInfluentTeam != currentInfluence) {
            return mostInfluentTeam;
        } else {
            return -1;
        }
    }
}
