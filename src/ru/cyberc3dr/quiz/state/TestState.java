package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.test.Test;

/**
 * Интерфейс состояния теста.
 * Реализует паттерн State.
 */
public interface TestState {

    void reset(Test test);
    boolean hasNextQuestion(Test test);
    Question getNextQuestion(Test test);
    void advanceQuestion(Test test);
    void printScore(Test test);
}
