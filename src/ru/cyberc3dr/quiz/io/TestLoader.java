package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.Test;

@FunctionalInterface
public interface TestLoader {
    Test load();
}
