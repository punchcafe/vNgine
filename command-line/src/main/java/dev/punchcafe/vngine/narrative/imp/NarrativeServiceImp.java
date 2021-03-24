package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import dev.punchcafe.vngine.narrative.NarrativeService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class NarrativeServiceImp implements NarrativeService<NarrativeImp> {

    private final Map<String, NarrativeImp> narratives;

    public NarrativeServiceImp(final Stream<NarrativeImp> allNarratives) {
        this.narratives = allNarratives
                .collect(toMap(NarrativeImp::getId, narrativeImp -> narrativeImp));
    }

    @Override
    public Narrative<NarrativeImp> getNarrative(String narrativeId) {
        return ofNullable(narratives.get(narrativeId)).map(Narrative::of).orElseThrow();
    }

    @Override
    public Set<String> allNarrativeIds() {
        return narratives.keySet();
    }
}
