package dev.punchcafe.vngine;

public interface NarrativeReader {

    /**
     * Allows platform independant intepretation of narrative elements
     * @param narrative
     */
    void readNarrative(Narrative narrative);
}
