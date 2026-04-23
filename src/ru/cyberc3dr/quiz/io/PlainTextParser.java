package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.data.MapQuestionData;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlainTextParser implements QuestionParser<String> {

    @Override
    public List<QuestionData> parse(String source) {
        List<QuestionData> result = new ArrayList<>();

        String[] blocks = source.split("\\n\\n");

        for(String block : blocks) {
            Map<String, String> map = new HashMap<>();

            String[] lines = block.split("\\n");
            for(String line : lines) {
                String[] parts = line.split(": ", 2);
                if(parts.length == 2) {
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }

            result.add(new MapQuestionData(map));
        }

        return result;
    }
}
