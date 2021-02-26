package dev.punchcafe.vngine;

import dev.punchcafe.vngine.node.SceneNode;

import java.util.Optional;

/**
 * The global game state. This class is responsible for keeping track of all state in the game, i.e. character
 * relationships. Typically, the {@link GameState} will allow a {@link SceneNode}
 */
public interface GameState {

    //TODO: throw if po=roperty missing

    int getIntegerProperty(final String property);
    String getClassificationProperty(final String propertyName);
    boolean getBooleanProperty(final String propertyName);
    // TODO: setters


    void changeIntegerPropertyBy(final String property, final int changeValue);
    void setBooleanProperty(final String property, final boolean value);
    void setClassificationProperty(final String property, final String value);

}
