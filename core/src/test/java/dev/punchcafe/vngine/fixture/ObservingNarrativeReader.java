package dev.punchcafe.vngine.fixture;

import dev.punchcafe.vngine.pom.narrative.Narrative;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;

public class ObservingNarrativeReader implements NarrativeReader<ObservingNarrative> {
    @Override
    public void readNarrative(Narrative<ObservingNarrative> narrative) {
        narrative.getContents().recordAsRead();
    }
}
