package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.test.Test;

@FunctionalInterface
public interface TestLoader {
    Test load();
}
