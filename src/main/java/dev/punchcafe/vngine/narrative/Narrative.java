package dev.punchcafe.vngine.narrative;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Narrative<T> {

    public static <T> Narrative<T> of(T contents){
        return new Narrative<>(contents);
    }

    public static <T> Stream<Narrative<T>> fromStream(Stream<T> contents){
        return contents.map(Narrative::new);
    }

    private final T contents;

    public T getContents(){
        return this.contents;
    };
}
