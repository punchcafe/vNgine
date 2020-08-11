package ascii.example;

import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.NarrativeReader;

public class AsciiNarrativeReader implements NarrativeReader {

    private static String NAME_PLATE = "%s:";

    @Override
    public void readNarrative(Narrative narrative) {
        narrative.forEach(narrativeElement -> {
            final var cast = (AsciiNarrativeElement) narrativeElement;
            System.out.println(String.format(NAME_PLATE, cast.getPlayerName(), cast.getMessage()));
            System.out.println();
            System.out.println(cast.getMessage());
            System.out.println();
            System.out.println();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
