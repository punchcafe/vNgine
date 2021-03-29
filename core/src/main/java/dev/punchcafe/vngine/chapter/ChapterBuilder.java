package dev.punchcafe.vngine.chapter;

import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.parse.yaml.ChapterConfig;
import dev.punchcafe.vngine.player.PlayerObserver;

public class ChapterBuilder<N> {
    private NarrativeService<N> narrativeService;
    private NarrativeReader<N> narrativeReader;
    private PlayerObserver playerObserver;

    public Chapter buildChapter(final ChapterConfig chapterConfig);
}
