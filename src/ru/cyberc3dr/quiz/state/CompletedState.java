package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.test.Test;

import java.util.Scanner;

/**
 * Состояние: тест завершен.
 */
public final class CompletedState implements TestState {

    @Override
    public void start(Test test, Scanner scanner) {
        System.out.println("Перед запуском выполните reset().");
    }

    @Override
    public void reset(Test test) {
        test.setCurrentScore(0);
        test.setState(new ReadyState());
    }

    @Override
    public void printScore(Test test) {
        test.getStrategy().grade(test.getCurrentScore(), test.getMaxScore());
    }
}
