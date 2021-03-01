package dev.punchcafe.vngine.parse.narrative;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.narrative.imp.NarrativeImp;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class NarrativeParser {

    public static Stream<dev.punchcafe.vngine.narrative.Narrative> parseNarrative(final File narrativeFile)
            throws IOException {
        final var mapper = new ObjectMapper(new YAMLFactory());
        final var narratives = mapper.readValue(narrativeFile, NarrativeConfig.class);
        return narratives.getNarrative().stream()
                .map(NarrativeParser::parseSingleNarrative);
    }

    private static dev.punchcafe.vngine.narrative.Narrative parseSingleNarrative(final Narrative yamlModel) {
        return NarrativeImp.builder()
                .id(yamlModel.getNarrativeId())
                .characterName(yamlModel.getCharacterName())
                .message(yamlModel.getDialogue())
                .build();
    }
}
