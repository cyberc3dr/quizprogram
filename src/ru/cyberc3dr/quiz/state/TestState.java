package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.test.Test;

import java.util.Scanner;

/**
 * Интерфейс состояния {@link Test}
 * <p>
 * Реализует паттерн State.
 */
public interface TestState {
    void start(Test test, Scanner scanner);

    void reset(Test test);

    void printScore(Test test);
}
