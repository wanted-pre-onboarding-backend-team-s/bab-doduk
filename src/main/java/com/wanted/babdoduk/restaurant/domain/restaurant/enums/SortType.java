package com.wanted.babdoduk.restaurant.domain.restaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {

    DISTANCE("distance", "거리순"),
    RATING("rating", "평점순");

    private static final Map<String, SortType> SORT_TYPE_MAP = new HashMap<>();

    static {
        for (SortType type : values()) {
            SORT_TYPE_MAP.put(type.name, type);
        }
    }

    private final String name;
    private final String description;

    @JsonCreator
    public static SortType of(String type) {
        return SORT_TYPE_MAP.get(type);
    }
}
