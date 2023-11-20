import org.junit.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GameFactoryTest {

    @Test
    public void test_creategame() throws IOException {
        TomJerryGame tjg = GameFactory.createGame();//target
        assertEquals(30,tjg.mazeMap.size());
    }

    @Test
    public void test_main() throws IOException, InterruptedException {
        String[] args = {"test"};
        GameFactory.main(args); //target
    }
}
