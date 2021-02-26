package dev.punchcafe.vngine.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.parse.yaml.GameConfig;

import java.io.File;
import java.io.IOException;

public class YamlReader {
    public static void main(String[] args) throws IOException {
        final var mapper = new ObjectMapper(new YAMLFactory());
        final var config = mapper.readValue(new File("src/main/resources/game-config/chapter_1.yaml"), GameConfig.class);
        System.out.println("Done!");
    }
}
