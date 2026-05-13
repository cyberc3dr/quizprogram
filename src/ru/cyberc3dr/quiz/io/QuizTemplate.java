package ru.cyberc3dr.quiz.io;

/**
 * Шаблон теста, который будут обрабатывать исполнители запросов.
 */
public final class QuizTemplate {

    private final String name;
    private final String path;

    public QuizTemplate(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
