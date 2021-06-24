package playtest.fixture;

import dev.punchcafe.vngine.pom.narrative.Narrative;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ObservingNarrativeService implements NarrativeService<ObservingNarrative> {

    private final ObservingNarrative observingNarrative = new ObservingNarrative();
    private Set<String> legalNarrativeIds;

    public ObservingNarrativeService(final Set<String> legalNarrativeIds) {
        this.legalNarrativeIds = legalNarrativeIds;
    }

    public ObservingNarrative getObservingNarrative(){
        return this.observingNarrative;
    }

    @Override
    public Narrative<ObservingNarrative> getNarrative(String narrativeId) {
        if (!legalNarrativeIds.contains(narrativeId)) {
            throw new RuntimeException();
        }
        return Narrative.of(this.observingNarrative.assumeId(narrativeId));
    }

    @Override
    public Set<String> allNarrativeIds() {
        return new HashSet<>(legalNarrativeIds);
    }
}
