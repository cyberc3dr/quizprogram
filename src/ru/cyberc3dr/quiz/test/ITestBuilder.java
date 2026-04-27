package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.tree.Node;

/**
 * Интерфейс для билдеров {@link Test}
 * (по книге Швеца)
 */
public interface ITestBuilder {
    ITestBuilder addNode(Node node);
    ITestBuilder setGradingStrategy(GradingStrategy strategy);
    Test build();
}
