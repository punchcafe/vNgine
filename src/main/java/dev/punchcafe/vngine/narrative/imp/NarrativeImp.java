package dev.punchcafe.vngine.narrative.imp;

import dev.punchcafe.vngine.narrative.Narrative;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple ASCII narrative implementation.
 */
@Getter
@AllArgsConstructor
public class NarrativeImp implements Narrative {
    private String message;
}
