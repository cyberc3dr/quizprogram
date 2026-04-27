package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;

/**
 * Интерфейс для билдеров тестов
 * (по книге Швеца)
 */
public interface ITestBuilder {
    ITestBuilder addQuestion(Question question);
    ITestBuilder setGradingStrategy(GradingStrategy strategy);
    Test build();
}
