package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import dev.punchcafe.vngine.narrative.NarrativeReader;

public class NarrativeReaderImp implements NarrativeReader {
    @Override
    public void readNarrative(Narrative narrative) {
        // TODO: use Generics to make this more user friendly without having to cast
        final var castNarrative = (NarrativeImp) narrative;
        if(castNarrative.getCharacterName() != null ){
            System.out.println(castNarrative.getCharacterName()+":");
        }
        System.out.println(castNarrative.getMessage());
        System.out.println("");
    }
}
