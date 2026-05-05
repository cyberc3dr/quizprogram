package ru.cyberc3dr.quiz.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
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
@Component("fileTestLoader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class FileTestLoader implements TestLoader {

    @Autowired
    private TestParser<String> parser;
    @Autowired
    private GradingStrategy strategy;

    private String path;

    public FileTestLoader(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Test load() {
        try {
            String content = Files.readString(Path.of(path));
            List<Node> nodes = parser.parse(content);
            TestBuilder builder = new TestBuilder(strategy);

            for (Node node : nodes) {
                builder.addNode(node);
            }

            return builder.build();
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
