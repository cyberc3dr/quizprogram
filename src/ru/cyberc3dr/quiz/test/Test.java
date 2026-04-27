package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;
import ru.cyberc3dr.quiz.state.CompletedState;
import ru.cyberc3dr.quiz.state.TestState;
import ru.cyberc3dr.quiz.tree.Node;
import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.tree.QuestionSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Тест состоящий из вопросов. Является контейнером для {@link Question} и {@link QuestionSection}.
 * <p>
 * Контекст для {@link GradingStrategy}
 * <p>
 * Использует {@link TestState} для управления состояниями теста.
 */
public final class Test implements Node {

    private final List<Node> children;
    private GradingStrategy strategy = new SumGradingStrategy();
    private TestState state;
    private int currentScore = 0;

    public Test(List<? extends Node> children) {
        this.children = new ArrayList<>(children);
        this.state = new CompletedState();
    }

    public void addQuestion(Question question) {
        this.children.add(question);
    }

    public void addSection(QuestionSection section) {
        this.children.add(section);
    }

    public void add(Node node) {
        this.children.add(node);
    }

    public void remove(Node node) {
        this.children.remove(node);
    }

    @Override
    public void run(TestRunContext context) {
        for (Node child : children) {
            child.run(context);
        }
    }

    @Override
    public int getMaxScore() {
        return children.stream()
                .mapToInt(Node::getMaxScore)
                .sum();
    }

    public void execute(Scanner scanner) {
        run(new TestRunContext(this, scanner));
    }

    public void run(Scanner scanner) {
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
