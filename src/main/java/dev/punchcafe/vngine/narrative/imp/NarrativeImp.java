package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Simple ASCII narrative implementation.
 */
@Getter
@Builder
@AllArgsConstructor
public class NarrativeImp implements Narrative {
    private final String id;
    private final String characterName;
    private final String message;
}
