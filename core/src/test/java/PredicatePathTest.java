import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PredicatePathTest extends PlayTest {
    @Override
    String gameConfigLocation() {
        return "core/src/test/resources/vng-game-1";
    }

    @BeforeAll
    void beforeAll(){
        this.buildGame();
    }

    @Test
    void canTickGame(){
        game.tick();
    }
}
