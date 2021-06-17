package dev.punchcafe.vngine.pom.narrative;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
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
