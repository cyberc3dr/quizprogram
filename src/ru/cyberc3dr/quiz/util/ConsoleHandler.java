package ru.cyberc3dr.quiz.util;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.test.Test;
import ru.cyberc3dr.quiz.test.TestHistory;
import ru.cyberc3dr.quiz.test.TestSnapshot;

import java.util.Scanner;

/**
 * Обработчик консольного ввода-вывода для теста.
 * Управляет историей снимков и взаимодействием с пользователем.
 */
public final class ConsoleHandler implements AutoCloseable {

    private final Scanner scanner;
    private final TestHistory history;

    public ConsoleHandler(Scanner scanner) {
        this.scanner = scanner;
        this.history = new TestHistory();
    }

    public void runTest(Test test) {
        test.reset();

        while (test.hasNextQuestion()) {
            Question question = test.getNextQuestion();
            showQuestion(question);

            System.out.print("Введите ответ (или 'back' для отката): ");
            String input = scanner.nextLine().trim();

            if ("back".equalsIgnoreCase(input)) {
                undo(test);
                continue; // остаемся на том же вопросе
            }

            saveSnapshot(test);
            int score = question.checkAnswer(input);
            test.addScore(score);
            test.advanceQuestion();

            System.out.println("Текущий счет: " + test.getCurrentScore());
        }

        test.printScore();
    }

    private void showQuestion(Question question) {
        System.out.println("\n" + question.getTitle());

        if (question.getOptions().size() == 1) {
            System.out.println("Ans: " + question.getOptions().getFirst());
        } else if (question.getOptions().size() > 1) {
            for (int i = 0; i < question.getOptions().size(); i++) {
                System.out.println((i + 1) + ". " + question.getOptions().get(i));
            }
        }
    }

    private void undo(Test test) {
        TestSnapshot snapshot = history.undo();
        if (snapshot != null) {
            test.restoreFromSnapshot(snapshot);
            System.out.println("Откат выполнен. Текущий счет: " + test.getCurrentScore());
        } else {
            System.out.println("Нет сохраненных снимков для отката.");
        }
    }

    private void saveSnapshot(Test test) {
        history.save(test.createSnapshot());
    }

    @Override
    public void close() {
        scanner.close();
    }
}
