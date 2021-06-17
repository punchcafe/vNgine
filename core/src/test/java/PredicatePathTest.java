import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Structure:
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PredicatePathTest extends PlayTest {
    @Override
    String gameConfigLocation() {
        return "src/test/resources/vng-game-1";
    }

    @BeforeAll
    void beforeAll(){
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);
        this.buildGame();
    }

    @Test
    void canTickGame(){
        game.startNewGame();
    }
}
