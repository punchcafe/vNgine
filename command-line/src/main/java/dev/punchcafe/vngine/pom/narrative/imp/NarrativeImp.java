package dev.punchcafe.vngine.pom.narrative.imp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Simple ASCII narrative implementation.
 */
@Getter
@Builder
@AllArgsConstructor
public class NarrativeImp {
    private final String id;
    private final String characterName;
    private final String message;
}
