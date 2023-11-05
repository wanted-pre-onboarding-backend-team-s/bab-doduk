package com.wanted.babdoduk.restaurant.domain.restaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {

    DISTANCE("distance", "거리순"),
    RATING("rating", "평점순");

    private final String name;
    private final String description;

    @JsonCreator
    public static SortType of(String input) {
        return Stream.of(SortType.values())
                     .filter(type -> type.toString().equals(input.toUpperCase()))
                     .findFirst()
                     .orElse(null);
    }
}
