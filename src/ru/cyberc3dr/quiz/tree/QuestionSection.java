package ru.cyberc3dr.quiz.tree;

import ru.cyberc3dr.quiz.test.TestRunContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Контейнер, содержащий {@link Question} и другие {@link QuestionSection}.
 * <p>
 * Представляет собой раздел в тесте, который может содержать вопросы и подразделы.
 */
public final class QuestionSection implements Container {

    private final String title;
    private final List<Node> children = new ArrayList<>();

    public QuestionSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void add(Node node) {
        children.add(node);
    }

    @Override
    public void remove(Node node) {
        children.remove(node);
    }

    @Override
    public void run(TestRunContext context) {
        System.out.println("\n== " + title + " ==");
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
}
