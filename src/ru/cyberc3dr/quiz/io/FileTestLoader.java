package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.test.Test;
import ru.cyberc3dr.quiz.test.TestBuilder;
import ru.cyberc3dr.quiz.tree.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Фасад для загрузки {@link Test} из файла.
 */
public final class FileTestLoader implements TestLoader {

    private final String path;
    private final TestParser<String> parser;

    public FileTestLoader(String path, TestParser<String> parser) {
        this.path = path;
        this.parser = parser;
    }

    public String getPath() {
        return path;
    }

    public TestParser<String> getParser() {
        return parser;
    }

    @Override
    public Test load() {
        try {
            String content = Files.readString(Path.of(path));
            List<Node> nodes = parser.parse(content);
            TestBuilder builder = new TestBuilder();

            for (Node node : nodes) {
                builder.addNode(node);
            }

            return builder.build();
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
