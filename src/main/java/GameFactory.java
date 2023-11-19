import game_scene.LandingPageView;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class GameFactory {
    public static final String MAP_FILE_PATH = "src/main/java/MazeData.csv";
    public static final String SP_OUTPUT_PATH = "src/main/java/shortest_path_at_beginning.csv";

    public static TomJerryGame createGame() throws IOException {

        return new TomJerryGame(MAP_FILE_PATH, SP_OUTPUT_PATH);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        /**Show landing page until user hits "Start Game"*/
        LandingPageView landingPageView = new LandingPageView();
        landingPageView.setVisible(true);
        LinkedBlockingQueue<String> queue = landingPageView.getController().getButtonHitRecords();
        while(true){

            String startButtonPressedRecord= queue.take();

            /**User presses start, create a new game instance*/
            TomJerryGame game = createGame();
            game.run(landingPageView);
            landingPageView.setVisible(true);
        }

    }

}
