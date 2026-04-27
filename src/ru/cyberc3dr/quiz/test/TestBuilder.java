package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;
import ru.cyberc3dr.quiz.tree.Node;
import ru.cyberc3dr.quiz.tree.QuestionSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Паттерн билдер для {@link Test}
 */
public final class TestBuilder implements ITestBuilder {

    private final List<Node> nodes = new ArrayList<>();
    private GradingStrategy strategy = new SumGradingStrategy();

    @Override
    public TestBuilder addNode(Node node) {
        this.nodes.add(node);
        return this;
    }

    @Override
    public TestBuilder setGradingStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public Test build() {
        if (nodes.isEmpty()) {
            throw new IllegalStateException("Test must contain at least one node");
        }

        Test test = new Test(nodes);
        test.setStrategy(strategy);
        return test;
    }
}
