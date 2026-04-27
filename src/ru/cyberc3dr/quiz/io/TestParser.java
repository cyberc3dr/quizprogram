package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.tree.Node;

import java.util.List;

/**
 * Функциональный интерфейс для парсеров {@link Node}
 * @param <T> тип источника
 */
@FunctionalInterface
public interface TestParser<T> {
    List<Node> parse(T source);
}
