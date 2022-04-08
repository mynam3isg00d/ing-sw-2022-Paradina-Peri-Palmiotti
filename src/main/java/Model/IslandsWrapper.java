package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the references to all the islands (model).
 * Allows changes to the model by providing island indexes
 */
public class IslandsWrapper {
    final private List<Island> islands;
    private int motherNaturePos;

    /**
     * Builds the IslandWrapper and initializes 12 empty Islands
     */
    public IslandsWrapper() {
        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Island newIsland = new Island();
            islands.add(newIsland);
        }
        motherNaturePos = 0;
        islands.get(0).setMotherNature(true);
    }

    /**
     * Replaces islands that need to be merged with the newly created island.
     * Adds the newly created island either on leftMostIndex (if the last island is on the right of the first one) or at the end of the list
     * @param leftmostIndex The index of the leftmost island that needs to be replaced
     * @param nToMerge How many islands need to be replaced
     */
    public void mergeIslands(int leftmostIndex, int nToMerge) {
        int initialSize = islands.size();

        int i;
        //removes the right islands from the islands list and merges them accordingly
        //i is the number of "merges with the next island" needed
        if (leftmostIndex + nToMerge < initialSize) {
            i = nToMerge;
        } else {
            i = initialSize - leftmostIndex;
        }

        Island newIsland = new Island(islands.get(leftmostIndex));
        islands.remove(leftmostIndex);
        //for every unit of i merges the island on leftmostindex and removes it from the list
        while (i > 1) {
            try {
                newIsland = new Island(newIsland, islands.get(leftmostIndex));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            islands.remove(leftmostIndex);
            i--;
        }

        //merges the islands on the left when needed
        for (int j = 0; j < nToMerge - (initialSize - leftmostIndex); j++) {
            try {
                newIsland = new Island(newIsland, islands.get(0));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            islands.remove(0);
        }


        //if possible adds the new island in the leftmost position between the old island's position, otherwise adds it at the end of the list
        if (leftmostIndex < islands.size()) {
            islands.add(leftmostIndex, newIsland);
        } else {
            islands.add(newIsland);
        }
    }

    /**
     * Adds students to a selected island
     * @param islandIndex The index of the selected island
     * @param students A list containing the students one wants to add
     */
    public void addStudents(int islandIndex, List<Student> students) {
        islands.get(islandIndex).addStudents(students);

        List<Island> islandModelView = new ArrayList<>(islands);
    }

    /**
     * Returns a representation of the students currently on the selected island
     * @param islandIndex The index of the selected island
     * @return An array, ordered by colorID, indicating how many students of each color the island contains
     */
    public int[] getStudents(int islandIndex) {
        return islands.get(islandIndex).getStudents();
    }

    /**
     * Returns the ID of the team which have a tower on the selected island
     * @param islandIndex The index of the selected island
     * @return An Integer containing either the team ID or null (if no towers are on the island)
     */
    public Integer getInfluence(int islandIndex) {
        return islands.get(islandIndex).getInfluence();
    }

    /**
     * Sets the influence of an island to the selected teamID
     * @param islandIndex The index of the selected island
     * @param team The ID of the team
     */
    public void setInfluence(int islandIndex, int team) {
        islands.get(islandIndex).setInfluence(team);
    }

    /**
     * Moves mother nature to a new position, after removing her from the old position
     * @param newPosition The position mother nature has reached
     */
    public void moveMotherNature(int newPosition) {
        islands.get(motherNaturePos).setMotherNature(false);
        motherNaturePos = newPosition;
        islands.get(newPosition).setMotherNature(true);
    }

    /**
     * Returns the number of islands currently existing
     * @return The number of islands
     */
    public int getIslandLength() {
        return islands.size();
    }

    /**
     * Returns a copy of the selected island
     * @param islandIndex The index of the selected island
     * @return A copy of the island
     */
    public Island getIsland(int islandIndex) {
        return new Island(islands.get(islandIndex));
    }

    /**
     * Returns the position of mother nature
     * @return The index of the island mother nature is on
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    /**
     * Returns the dimension of the selected island
     * @param islandIndex The index of the selected island
     * @return The dimension of the selected island
     */
    public int getIslandDimension(int islandIndex) {
        return islands.get(islandIndex).getDimension();
    }
}
