package dev.punchcafe.vngine;

import java.util.Iterator;
import java.util.List;

/**
 * Contains all narrative elements of a single node. When playing, the {@link GameController} will
 * iterate through all {@link NarrativeElement}s, before calling prompt.
 */
public class Narrative<T extends NarrativeElement> implements Iterable<T> {

    List<T> narrativeElements;

    public Narrative(final List<T> narrativeElements){
        this.narrativeElements = narrativeElements;
    }

    @Override
    public Iterator<T> iterator(){
        return narrativeElements.iterator();
    };
}
