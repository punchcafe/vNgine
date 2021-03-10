package dev.punchcafe.vngine.narrative;

import java.util.Set;

public interface NarrativeService<T> {
    Narrative<T> getNarrative(String narrativeId);
    Set<String> allNarrativeIds();
}
