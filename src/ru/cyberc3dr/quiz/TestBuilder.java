package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Паттерн билдер для {@link Test}
 */
public final class TestBuilder {
    private List<Question> questions = new ArrayList<>();
    private GradingStrategy strategy = new SumGradingStrategy();

    public TestBuilder addQuestion(Question question) {
        this.questions.add(question);
        return this;
    }

    public TestBuilder setGradingStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Test build() {
        Test test = new Test(questions);
        test.setStrategy(strategy);
        return test;
    }
}
