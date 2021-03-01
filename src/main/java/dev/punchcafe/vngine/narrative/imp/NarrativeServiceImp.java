package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import dev.punchcafe.vngine.narrative.NarrativeService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class NarrativeServiceImp implements NarrativeService {

    private final Map<String, Narrative> narratives;

    public NarrativeServiceImp(final Stream<Narrative> allNarratives){
        this.narratives = allNarratives.map(narrative -> (NarrativeImp) narrative)
                .collect(toMap(NarrativeImp::getId, identity()));
    }

    @Override
    public Narrative getNarrative(String narrativeId) {
        return ofNullable(narratives.get(narrativeId)).orElseThrow();
    }

    @Override
    public Set<String> allNarrativeIds() {
        return narratives.keySet();
    }
}
