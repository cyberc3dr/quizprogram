package ru.cyberc3dr.quiz.io;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Загрузка шаблона теста из файловой системы.
 */
public final class QuizTemplateLoader {

    public static QuizTemplate loadFromPath(String path) {
        Path quizPath = Path.of(path);
        if (!Files.exists(quizPath)) {
            throw new IllegalArgumentException("Файл теста не найден: " + path);
        }
        return new QuizTemplate(quizPath.getFileName().toString(), quizPath.toString());
    }
}
