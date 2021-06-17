package dev.punchcafe.vngine.fixture;

import java.util.ArrayList;
import java.util.List;

public class ObservingNarrative {
    private String currentId;
    private List<String> readIds = new ArrayList<>();

    public ObservingNarrative assumeId(final String newId){
        this.currentId = newId;
        return this;
    }

    public void recordAsRead(){
        this.readIds.add(currentId);
    }

    public List<String> getAllReadNarratives(){
        return new ArrayList<>(this.readIds);
    }

    public void reset(){
        this.currentId = null;
        this.readIds = new ArrayList<>();
    }
}
