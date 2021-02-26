package dev.punchcafe.vngine.old;

import java.util.Collection;

/**
 * A NarrativeElement constitutes a single congruent moment in a Nodes narrative.
 * It may contain an arbitrary number of props which a {@link NarrativeReader} will be able
 * to display for the specific platform. All props in a narrative element are understood to
 * be happening all at once.
 */
public interface NarrativeElement {

    Collection<Object> getAllProps();
}
