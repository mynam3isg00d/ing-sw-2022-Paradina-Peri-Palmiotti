package Model;

import View.IslandView;

import java.util.ArrayList;
import java.util.List;

public class IslandsWrapper {
    final private List<Island> islands;
    private IslandView islandView;
    private int motherNaturePos;

    public IslandsWrapper() {
        islands = new ArrayList<Island>();
        for (int i = 0; i < 12; i++) {
            Island newIsland = new Island();
            islands.add(newIsland);
        }
        motherNaturePos = 0;
    }

    //public because will be called by the controller
    public void mergeIslands(int leftmostIndex, int nToMerge) {
        int initialSize = islands.size();

        int i;
        //removes the right islands from the islands list and merges them accordingly
        if (leftmostIndex + nToMerge < initialSize) {
            i = nToMerge;
        } else {
            i = initialSize - leftmostIndex;
        }

        Island newIsland = new Island(islands.get(leftmostIndex));
        islands.remove(leftmostIndex);
        while (i > 1) {
            newIsland = new Island(newIsland, islands.get(leftmostIndex));
            islands.remove(leftmostIndex);
            i--;
        }

        for (int j = 0; j < nToMerge - (initialSize - leftmostIndex); j++) {
            newIsland = new Island(newIsland, islands.get(leftmostIndex));
            islands.remove(0);
        }


        //if possible adds the new island in the leftmost position between the old island's position, otherwise adds it at the and of the list
        if (leftmostIndex < islands.size()) {
            islands.add(leftmostIndex, newIsland);
        } else {
            islands.add(newIsland);
        }

        List<Island> islandModelView = new ArrayList<>(islands);
        islandView.update(islandModelView);
    }

    public void addStudents(int islandIndex, List<Student> students) {
        islands.get(islandIndex).addStudents(students);

        List<Island> islandModelView = new ArrayList<>(islands);
        islandView.update(islandModelView);
    }

    public void moveMotherNature(int newPosition) {
        islands.get(motherNaturePos).setMotherNature(false);
        motherNaturePos = newPosition;
        islands.get(newPosition).setMotherNature(true);

        List<Island> islandModelView = new ArrayList<>(islands);
        islandView.update(islandModelView);
    }

    public int getIslandLength() {
        return islands.size();
    }

    public void connectView(IslandView iv) {
        islandView = iv;
    }
}
