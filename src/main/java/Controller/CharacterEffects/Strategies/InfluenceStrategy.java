package Controller.CharacterEffects.Strategies;

import Controller.BoardsController;
import Model.IslandsWrapper;

public interface InfluenceStrategy extends Strategy{
    int calcInfluence(int islandIndex, IslandsWrapper islandModel, BoardsController boardsController);
}
