package ru.cyberc3dr.quiz.scoring;

import ru.cyberc3dr.quiz.Test;

/**
 * Стратегия оценки для {@link ru.cyberc3dr.quiz.Test}
 */
public interface GradingStrategy {
    void grade(Test test, int score);
}
