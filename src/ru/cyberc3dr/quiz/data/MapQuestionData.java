package ru.cyberc3dr.quiz.data;

import ru.cyberc3dr.quiz.util.NumberStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MapQuestionData implements QuestionData {

    private final Map<String, String> data;

    public MapQuestionData(Map<String, String> data) {
        this.data = data;
    }

    public String get(String key) {
        String value = data.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key '" + key + "' not found in question data.");
        }
        return value;
    }

    public Integer getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public Set<Integer> getIntSet(String key) {
        return NumberStringUtils.parseNumberSet(get(key));
    }

    public List<Integer> getIntList(String key) {
        return NumberStringUtils.parseNumberList(get(key));
    }

    @Override
    public List<String> getStringList(String key) {
        return Arrays.stream(get(key).split("\\|"))
                .map(String::trim)
                .toList();
    }
}
