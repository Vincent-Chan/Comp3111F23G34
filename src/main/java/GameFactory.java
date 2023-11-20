import game_scene.LandingPageView;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class GameFactory {
    public static String MAP_FILE_PATH = "src/main/java/MazeData.csv";
    public static String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning.csv";

    /**
     * Create a game instance with specified output path for map file and shortest path file
     * */
    public static TomJerryGame createGame() throws IOException {
        return new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        boolean unittesting_from_main = false;
        if(args.length>0 && args[0].equals("test")){
            System.out.println("testing");
            unittesting_from_main = true;
            MAP_FILE_PATH = "src/main/java/MazeData_test.csv";
            SP_OUTPUT_PATH= "src/main/java/shortest_path_at_beginning_test.csv";
        }

        /**Show landing page until user hits "Start Game"*/
        LandingPageView landingPageView = new LandingPageView();
        landingPageView.setVisible(true);
        if(unittesting_from_main)
            landingPageView.setVisible(false);
        LinkedBlockingQueue<String> queue = landingPageView.getController().getButtonHitRecords();
        while(true){
            if(unittesting_from_main){
                queue.put("s");
            }
            String startButtonPressedRecord= queue.take();

            /**User presses start, create a new game instance*/
            TomJerryGame game = createGame();
            game.run(landingPageView, false,unittesting_from_main,null);
            landingPageView.setVisible(true);
            if(unittesting_from_main){
                landingPageView.setVisible(false);
                break;
            }
        }
    }

}