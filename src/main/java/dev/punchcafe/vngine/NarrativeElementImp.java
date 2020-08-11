package dev.punchcafe.vngine;

import java.util.Collection;
import java.util.Map;

/**
 * A default implementation of the {@link NarrativeElement} interface. When better type safety is desired,
 * it's better to implement this yourself.
 *
 */
public class NarrativeElementImp {
    private Map<String, Object> propMap;

    public <T> T getProp(String propName) {
        final var entry = propMap.get(propName);
        if (entry == null) {
            return null;
        }
        try {
            return (T) entry;
        } catch (ClassCastException ex) {
            return null;
        }
    }

    public Collection<Object> getAllProps(){
        return propMap.values();
    };
}
