package ru.cyberc3dr.quiz.scoring;

import ru.cyberc3dr.quiz.test.Test;

/**
 * Стратегия оценки для {@link Test}
 */
public interface GradingStrategy {
    void grade(int score, int maxScore);
}
