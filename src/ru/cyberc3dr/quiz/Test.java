package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;

import java.util.List;
import java.util.Scanner;

/**
 * Тест состоящий из вопросов
 * Является композитом для объектов типа {@link Question}
 * Паттерн не является полным, т.к. нет типа Component.
 * Это не требуется, т.к. тесты не могут быть вложены друг в друга.
 * Контекст для стратегий оценки.
 */
public final class Test {

    private final List<Question> questions;
    private GradingStrategy strategy = new SumGradingStrategy();

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getMaxScore() {
        return questions.stream()
                .mapToInt(Question::getScore)
                .sum();
    }

    public GradingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
    }

    public int run() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for(Question question : questions) {
            score += question.ask(scanner);
        }

        return score;
    }

    public void printScore(int score) {
        strategy.grade(this, score);
    }
}
