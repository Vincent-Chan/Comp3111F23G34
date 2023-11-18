package visuals;

public class StringResources {
    public static String tom = "src/main/java/visuals/tom.png";
    public static String jerry = "src/main/java/visuals/jerry.png";
    public static String select_difficulty = "src/main/java/visuals/difficult_selection.png";
    public static String logo_image = "src/main/java/visuals/logo.png";
    public static String tom_catches_jerry = "src/main/java/visuals/tom_catches_jerry.png";
    public static String tom_angry = "src/main/java/visuals/tom_angry.png";
    public static String jerry_wins = "src/main/java/visuals/jerry_win_image.png";
    public static String invalid_move = "src/main/java/visuals/jerry_invalid_move.png";
    public static String show_sp_hint = "The GREEN blocks highlights the shortest path to exit.\n This hint will disappear once you make your first move";
    public static String show_sp_hint_image = "src/main/java/visuals/hint.png";
    public static String clear_vertex = "CLEAR";
    public static String barrier = "BARRIER";
    public static String sp_component = "SP";
    public static String showRemainingMoves(int playerID, int remainingRounds){
        if(playerID == 0){
            return "Tom's round: "+remainingRounds+" left.";
        }
        else{
            return "Jerry's round: "+remainingRounds+" left.";
        }
    }

    public static String gamestarts = "Welcome to the GAME!";

    public static String jerryWinsMessage = "Congratulations! You win";
    public static String tomWinsMessage = "O-oh... Tom caught you!";
}
