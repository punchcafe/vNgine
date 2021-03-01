package dev.punchcafe.vngine.narrative;

import java.util.Set;

public interface NarrativeService {
    Narrative getNarrative(String narrativeId);
    Set<String> allNarrativeIds();
}
