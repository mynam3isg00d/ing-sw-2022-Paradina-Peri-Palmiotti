package Util;

import java.util.regex.Pattern;

public class HelpInterpreter {

    public static boolean isHelp(String line) {
        Pattern help = Pattern.compile("^(help)");
        return help.matcher(line).find();
    }

    public static String getHelpMessage(String help) {
        if(Pattern.compile("^(help 1)$").matcher(help).find()) {
            return "Available Commands:\n" +
                    "choose wizard X\n" +
                    "play assistant X\n" +
                    "move student X to dining - X is the position in the Entrance\n" +
                    "page 1/2";
        } else if (Pattern.compile("^(help 2)$").matcher(help).find()) {
            return "move student X to island Y - X is the position in the Entrance\n" +
                    "move mother nature X - X is the number of steps\n" +
                    "pick cloud X\n" +
                    "buy character X - X is index in the shop (not charID!)\n" +
                    "page 2/2";
        } else if (Pattern.compile("^(help char)").matcher(help).find()) {
            int charID = Integer.parseInt(help.split(" ")[2]);
            switch (charID) {
                case 0: return "Take 1 Student from this card and place it on an Island of your choice.\n" +
                        "buy character X [student index] [island index]";
                case 1: return "During this turn, you take control of any number of Professors even if you have the smae number of Students as the player who currently controls them.\n" +
                        "buy character ?";
                case 2: return "Choose an Island and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved.\n" +
                        "buy character X [island index]";
                case 3: return "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played\n" +
                        "buy character X";
                case 4: return "Place a No Entry tile on and Island of your choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card. Influence is not caluclated.\n" +
                        "buy character X [island index]";
                case 5: return "When resolving a Conquering on an Island, Towers do not count towards influence.\n" +
                        "buy character X";
                case 6: return "You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance\n" +
                        "buy character X [card index] (x 1,2,3) [entrance index] (x 1,2,3)";
                case 7: return "During the influcence calculation this turn, you count as having 2 more influence.\n" +
                        "buy character X";
                case 8: return "Choose a color of a Student: during the influence calculation this turn, that color adds no influence.\n" +
                        "buy character X [color ID]";
                case 9: return "You may exchange up to 2 Students between your Entrance and your Dining Room.\n" +
                        "buy character X [entrance index] (x 1,2) [dining index] (x 1,2)";
                case 10: return "Take 1 Student from this card and place it in your Dining Room.\n" +
                        "buy character X [card index]";
                case 11: return "Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag. If any player has fewer than 3 Students of that type, return as many Students as they have.\n" +
                        "buy character X [color ID]";
                default: return "There is no such character!";
            }
        }
        return  "help 1 for page 1\n" +
                "help 2 for page 2\n" +
                "help char [character id] for char info";
    }
}
