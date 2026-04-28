package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.state.ReadyState;
import ru.cyberc3dr.quiz.state.TestState;
import ru.cyberc3dr.quiz.tree.QuestionSection;

import java.util.Scanner;

/**
 * Тест состоящий из вопросов. Содержит корневой {@link QuestionSection}
 * и делегирует ему запуск и подсчет максимального балла.
 * <p>
 * Контекст для {@link GradingStrategy}
 * <p>
 * Использует {@link TestState} для управления состояниями теста.
 */
public final class Test {

    private final QuestionSection rootSection;
    private GradingStrategy strategy;
    private TestState state = new ReadyState();
    private int currentScore = 0;

    public Test(QuestionSection rootSection, GradingStrategy strategy) {
        this.rootSection = rootSection;
        this.strategy = strategy;
    }

    public void run(TestRunContext context) {
        rootSection.run(context);
    }

    public int getMaxScore() {
        return rootSection.getMaxScore();
    }

    public void start(Scanner scanner) {
        state.start(this, scanner);
    }

    public GradingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
    }

    public TestState getState() {
        return state;
    }

    public void setState(TestState state) {
        this.state = state;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void addScore(int score) {
        this.currentScore += score;
    }

    public void reset() {
        state.reset(this);
    }

    public void printScore() {
        state.printScore(this);
    }
}
