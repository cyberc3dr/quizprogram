package ru.cyberc3dr.quiz.answer;

/**
 * Общий интерфейс для всех видов ответа.
 * <p>
 * Паттерн стратегия для {@link ru.cyberc3dr.quiz.tree.Question}.
 * Ответ можно проверять по разному.
 */
public interface Answer {
    boolean check(String input);
}
