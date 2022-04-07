package Model;
import View.IslandView;

import java.util.*;

/**
 * Island objects represent the model of an island of the game
 */
public class Island { //è osservato da islandview, quindi ogni modifica chiama notify su view
    private int dimension;
    private int[] students;
    private Integer influenceTeam;
    private boolean motherNature;
    private IslandView islandView;

    //builds an empty island, dimension is initialized 1

    /**
     * Builds an empty island, with no students, no towers, no mother nature on top and dimension = 1
     */
    public Island() {
        dimension = 1;
        students = new int[5];
        influenceTeam = null;
        motherNature = false;
    }

    /**
     * Builds an island identical to i.
     * Mainly used for debugging
     * @param i The island one wants to copy
     */
    public Island(Island i) {
        dimension = i.getDimension();
        students = i.getStudents();
        influenceTeam = i.getInfluence();
        motherNature = i.isMotherNature();
    }

    //merges two islands: creates the resulting island. The dimension of the resulting island is sum of the dimensions of the other two

    /**
     * Merges i1 and i2 in order to build a new island.
     * The new island will have all the students on both the islands, the influence unchanged (both should have the same influence) and the dimension equals to the sum of the two dimensions
     * @param i1 The first Island to merge
     * @param i2 The second Island to merge
     */
    public Island(Island i1, Island i2) {
        dimension = i1.getDimension() + i2.getDimension();

        students = new int[5];
        int[] students1;
        int[] students2;
        students1 = i1.getStudents();
        students2 = i2.getStudents();

        for (int i = 0; i < 5; i++) {
            students[i] = students1[i] + students2[i];
        }

        influenceTeam = i1.getInfluence();
        motherNature = true;
    }

    /**
     * Returns the dimension of the island
     * @return The dimension of the island
     */
    public int getDimension() {
        return dimension;
    }

    //methods regarding students attribute

    /**
     * Returns a representation of the students currently on the selected island
     * @return An array, ordered by colorID, indicating how many students of each color the island contains
     */
    public int[] getStudents() {
        return Arrays.copyOf(students, 5);
    }

    /**
     * Adds new students to the Island
     * @param newStudents A list of students one wants to add
     */
    public void addStudents(List<Student> newStudents) {
        for(Student s : newStudents) {
            students[s.getColorId()]++;
        }
    }

    /**
     * Returns the ID of the team which have a tower on the Island
     * @return An Integer containing either the team ID or null (if no towers are on the island)
     */
    public Integer getInfluence() {
        return influenceTeam;
    }

    /**
     * Sets the influence the Island to the selected teamID
     * @param team The ID of the team
     */
    public void setInfluence(int team) {
        influenceTeam = Integer.valueOf(team);
    }

    /**
     * Changes the isMotherNature attribute
     * @param n TRUE if motherNature is now on the island, FALSE otherwise
     */
    public void setMotherNature(boolean n) {
        motherNature = n;
    }

    /**
     * Tells whether mother nature is on the Island
     * @return TRUE if mother nature is on the Island, FALSE otherwise
     */
    public boolean isMotherNature() {
        return motherNature;
    }

    //only used in testing, in an actual game two islands with the same state ARE NOT equal
    public boolean equals(Island i) {
        if (influenceTeam != i.influenceTeam) return false;
        if (dimension != i.dimension) return false;
        if (motherNature != i.motherNature) return false;
        for (int j = 0; j<5; j++) {
            if(students[j] != i.getStudents()[j]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String out = (
                "|  Dimension: " + dimension + " |\n" +
                "|  Team Influence: " + influenceTeam + "  |\n" +
                "|  MotherNature: " + motherNature + "  |\n" +
                "|  Students: "
        );

        out = out.concat(" Y= " + students[0]);
        out = out.concat(" B= " + students[1]);
        out = out.concat(" G= " + students[2]);
        out = out.concat(" R= " + students[3]);
        out = out.concat(" P= " + students[4]);

        out = out.concat(" |\n");
        return out;
    }
}
