package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Паттерн билдер для {@link Test}
 */
public final class TestBuilder implements ITestBuilder {

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
        if(questions.isEmpty())
            throw new IllegalStateException("Test must contain at least one question");

        Test test = new Test(questions);
        test.setStrategy(strategy);
        return test;
    }
}
