package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.test.Test;
import ru.cyberc3dr.quiz.test.TestRunContext;

import java.util.Scanner;

/**
 * Состояние: тест готов к прохождению.
 */
public final class ReadyState implements TestState {

    @Override
    public void start(Test test, Scanner scanner) {
        test.setCurrentScore(0);
        test.setState(new ActiveState());

        test.run(new TestRunContext(test, scanner));

        test.setState(new CompletedState());
    }

    @Override
    public void reset(Test test) {
        test.setCurrentScore(0);
    }

    @Override
    public void printScore(Test test) {
        System.out.println("Тест еще не начат. Пройдите тест для получения результата.");
    }
}
