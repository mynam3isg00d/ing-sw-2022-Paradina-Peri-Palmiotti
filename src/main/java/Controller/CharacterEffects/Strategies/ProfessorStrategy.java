package Controller.CharacterEffects.Strategies;

import Model.Board;
import Model.Player;

import java.util.HashMap;
import java.util.List;

public interface ProfessorStrategy {
    public void updateProfessors(Player[] professors, HashMap<String, Board> playerBoardMap, List<Player> players);
}
