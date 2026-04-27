package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.test.Test;

import java.util.Scanner;

/**
 * Состояние: тест в стадии прохождения.
 */
public final class ActiveState implements TestState {

    @Override
    public void start(Test test, Scanner scanner) {
        System.out.println("Тест уже запущен. Продолжайте отвечать на вопросы.");
    }

    @Override
    public void reset(Test test) {
        test.setCurrentScore(0);
        test.setState(new ReadyState());
    }

    @Override
    public void printScore(Test test) {
        System.out.println("Тест еще не завершен. Пройдите все вопросы для получения результата.");
    }
}
