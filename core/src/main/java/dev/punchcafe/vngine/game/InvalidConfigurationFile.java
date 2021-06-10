package dev.punchcafe.vngine.game;

public class InvalidConfigurationFile extends RuntimeException {
    public InvalidConfigurationFile(final String missingFileName){
        super(String.format("Couldn't parse configuration file: %s", missingFileName));
    }
}
