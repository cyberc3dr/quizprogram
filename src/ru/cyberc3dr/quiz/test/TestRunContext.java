package ru.cyberc3dr.quiz.test;

import java.util.Scanner;

/**
 * Контекст запуска {@link Test}
 * <p>
 * Требуется для обхода сложной структуры.
 */
public final class TestRunContext {

    private final Test test;
    private final Scanner scanner;

    public TestRunContext(Test test, Scanner scanner) {
        this.test = test;
        this.scanner = scanner;
    }

    public Test getTest() {
        return test;
    }

    public String readLine() {
        return scanner.nextLine();
    }
}
