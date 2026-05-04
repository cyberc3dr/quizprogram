package ru.cyberc3dr.quiz.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.data.MapQuestionData;
import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.factory.QuestionFactory;
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
@Component("plainText")
@Primary
public final class PlainTestParser implements TestParser<String> {

    public static final String COMMENT = "#";
    public static final String MENU_START = "menu ";
    public static final String MENU_END = "endmenu";
    public static final String QUESTION_START = "question";
    public static final String QUESTION_END = "endquestion";

    @Autowired
    private Map<String, QuestionFactory> map;

    private Question createQuestion(QuestionData data) {
        QuestionFactory factory = map.get(data.get("QuestionType"));

        if(factory == null) {
            throw new IllegalArgumentException("Unknown question type: " + data.get("QuestionType"));
        }

        return factory.createQuestion(data);
    }

    @Override
    public List<Node> parse(String source) {
        String[] lines = source
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .split("\n");

        List<Node> rootNodes = new ArrayList<>();
        Deque<QuestionSection> sectionStack = new ArrayDeque<>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty() || line.startsWith(COMMENT)) continue;

            if (line.startsWith(MENU_START)) {
                QuestionSection section = new QuestionSection(parseMenuTitle(line, i + 1));
                if (sectionStack.isEmpty()) {
                    rootNodes.add(section);
                } else {
                    sectionStack.peek().add(section);
                }
                sectionStack.push(section);
                continue;
            }

            if (line.equals(MENU_END)) {
                if (sectionStack.isEmpty()) {
                    throw new IllegalArgumentException("Unexpected endmenu at line " + (i + 1));
                }
                sectionStack.pop();
                continue;
            }

            if (line.equals(QUESTION_START)) {
                Map<String, String> data = new HashMap<>();
                int questionStartLine = i + 1;
                i++;

                while (i < lines.length) {
                    String questionLine = lines[i].trim();

                    if (questionLine.isEmpty() || questionLine.startsWith("#")) {
                        i++;
                        continue;
                    }

                    if (questionLine.equals(QUESTION_END)) break;

                    if (questionLine.equals(QUESTION_START)
                            || questionLine.startsWith(MENU_START)
                            || questionLine.equals(MENU_END)) {
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

                if (i >= lines.length || !lines[i].trim().equals(QUESTION_END)) {
                    throw new IllegalArgumentException("Question block started at line "
                            + questionStartLine + " is missing endquestion");
                }

                Question question = createQuestion(new MapQuestionData(data));
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
