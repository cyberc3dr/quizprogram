package ru.cyberc3dr.quiz.tree;

import ru.cyberc3dr.quiz.test.TestRunContext;

/**
 * Общий интерфейс элементов дерева.
 */
public interface Node {
    void run(TestRunContext context);
    int getMaxScore();
}
