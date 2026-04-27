package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.data.MapQuestionData;
import ru.cyberc3dr.quiz.factory.QuestionFactoryRegistry;
import ru.cyberc3dr.quiz.tree.Node;
import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.tree.QuestionSection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Парсер {@link Node} из строки.
 */
public final class PlainTestParser implements TestParser<String> {

    @Override
    public List<Node> parse(String source) {
        String[] lines = source
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .split("\n");

        List<Node> rootNodes = new ArrayList<>();
        Deque<QuestionSection> sectionStack = new ArrayDeque<>();
        QuestionFactoryRegistry registry = QuestionFactoryRegistry.getInstance();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty() || line.startsWith("#")) continue;

            if (line.startsWith("menu ")) {
                QuestionSection section = new QuestionSection(parseMenuTitle(line, i + 1));
                if (sectionStack.isEmpty()) {
                    rootNodes.add(section);
                } else {
                    sectionStack.peek().add(section);
                }
                sectionStack.push(section);
                continue;
            }

            if (line.equals("endmenu")) {
                if (sectionStack.isEmpty()) {
                    throw new IllegalArgumentException("Unexpected endmenu at line " + (i + 1));
                }
                sectionStack.pop();
                continue;
            }

            if (line.equals("question")) {
                Map<String, String> data = new HashMap<>();
                int questionStartLine = i + 1;
                i++;

                while (i < lines.length) {
                    String questionLine = lines[i].trim();

                    if (questionLine.isEmpty() || questionLine.startsWith("#")) {
                        i++;
                        continue;
                    }

                    if (questionLine.equals("endquestion")) break;

                    if (questionLine.equals("question")
                            || questionLine.startsWith("menu ")
                            || questionLine.equals("endmenu")) {
                        throw new IllegalArgumentException("Question block started at line "
                                + questionStartLine + " is missing endquestion");
                    }

                    String[] parts = questionLine.split(":\\s*", 2);
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid question property at line " + (i + 1)
                                + ". Expected 'Key: Value'");
                    }
                    data.put(parts[0].trim(), parts[1].trim());
                    i++;
                }

                if (i >= lines.length || !lines[i].trim().equals("endquestion")) {
                    throw new IllegalArgumentException("Question block started at line "
                            + questionStartLine + " is missing endquestion");
                }

                Question question = registry.create(new MapQuestionData(data));
                if (sectionStack.isEmpty()) {
                    rootNodes.add(question);
                } else {
                    sectionStack.peek().add(question);
                }
                continue;
            }

            throw new IllegalArgumentException("Unknown directive at line " + (i + 1) + ": " + line);
        }

        if (!sectionStack.isEmpty()) {
            throw new IllegalArgumentException("There is at least one unclosed menu block");
        }

        if (rootNodes.isEmpty()) {
            throw new IllegalArgumentException("Quiz must contain at least one question");
        }

        return rootNodes;
    }

    private String parseMenuTitle(String menuLine, int lineNumber) {
        String title = menuLine.substring("menu".length()).trim();
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Menu title is missing at line " + lineNumber);
        }

        if (title.length() >= 2 && title.startsWith("\"") && title.endsWith("\"")) {
            return title.substring(1, title.length() - 1);
        }

        return title;
    }
}
