package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import dev.punchcafe.vngine.narrative.NarrativeService;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class NarrativeServiceImp implements NarrativeService {

    private final Map<String, NarrativeImp> narrative = Map.of(
            "1", new NarrativeImp("First"),
            "2", new NarrativeImp("Second"),
            "3", new NarrativeImp("Third"),
            "4", new NarrativeImp("Fourth"),
            "5", new NarrativeImp("Fifth"));

    @Override
    public Narrative getNarrative(String narrativeId) {
        return ofNullable(narrative.get(narrativeId)).orElseThrow();
    }
}
