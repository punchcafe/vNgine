package dev.punchcafe.vngine.chapter;

import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Builder
public class ChapterConfigCache {
    private final Map<String, ChapterConfig> chapterConfigMap;
    @Getter
    private final ChapterConfig firstChapter;

    public Optional<ChapterConfig> get(final String id) {
        return Optional.ofNullable(chapterConfigMap.get(id));
    }
}
