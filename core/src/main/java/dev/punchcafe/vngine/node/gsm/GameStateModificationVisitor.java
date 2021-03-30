package dev.punchcafe.vngine.node.gsm;

public interface GameStateModificationVisitor<T> {

    T visitChangeIntegerProperty(final ChangeIntegerProperty changeIntegerProperty);
    T visitSetBooleanProperty(final SetBooleanProperty changeIntegerProperty);
    T visitSetStringProperty(final SetStringProperty changeIntegerProperty);
    T visitChangeChapterState(final ChangeChapterState changeChapterState);
}
