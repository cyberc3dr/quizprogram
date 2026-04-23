package ru.cyberc3dr.quiz.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class предназначенный для работы со строками.
 */
public final class NumberStringUtils {

    public static Set<Integer> parseNumberSet(String input) throws NumberFormatException {
        return Stream.of(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public static List<Integer> parseNumberList(String input) throws NumberFormatException {
        return Stream.of(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
