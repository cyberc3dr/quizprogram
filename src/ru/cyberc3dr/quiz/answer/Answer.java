package ru.cyberc3dr.quiz.answer;

/**
 * Общий интерфейс для всех видов ответа.
 * Является реализацией паттерна - стратегия.
 * Ответ можно проверять по разному.
 */
public interface Answer {
    boolean check(String input);
}
