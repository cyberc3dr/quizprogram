package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.QuestionFactoryRegistry;
import ru.cyberc3dr.quiz.Test;
import ru.cyberc3dr.quiz.TestBuilder;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Фасад для загрузки теста из файла.
 */
public final class FileTestLoader implements TestLoader {

    private final String path;
    private final QuestionParser<String> parser;

    public FileTestLoader(String path, QuestionParser<String> parser) {
        this.path = path;
        this.parser = parser;
    }

    public String getPath() {
        return path;
    }

    public QuestionParser<String> getParser() {
        return parser;
    }

    @Override
    public Test load() {
        try {
            String content = Files.readString(Path.of(path));

            List<QuestionData> dataList = parser.parse(content);

            QuestionFactoryRegistry registry = QuestionFactoryRegistry.getInstance();
            TestBuilder builder = new TestBuilder();

            for(QuestionData data : dataList) {
                builder.addQuestion(registry.create(data));
            }

            return builder.build();
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
