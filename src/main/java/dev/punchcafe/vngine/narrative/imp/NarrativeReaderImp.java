package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import dev.punchcafe.vngine.narrative.NarrativeReader;

public class NarrativeReaderImp implements NarrativeReader<NarrativeImp> {
    @Override
    public void readNarrative(Narrative<NarrativeImp> narrative) {
        final var contents = narrative.getContents();
        if(contents != null ){
            System.out.println(contents.getCharacterName()+":");
        }
        System.out.println(contents.getMessage());
        System.out.println("");
    }
}
