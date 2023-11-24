import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GameFactoryTest {

    @Test
    public void test_creategame() throws IOException {
        TomJerryGame tjg = GameFactory.createGame(); //target function
        assertEquals(30,tjg.mazeMap.size());
    }

    @Test
    public void test_main() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        String[] args = {"test"};
        GameFactory.main(args); //target function
    }
}

