package ru.cyberc3dr.quiz.data;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс позволяющий тащить данные по вопросу.
 * Можно сделать реализации для Map, ResultSet.
 * Так же можно сделать реализацию для JSON используя GSON либо FasterXML.
 * Ещё можно использовать YAML с библиотекой SnakeYAML.
 * <p>
 * В проекте представлена реализация для Map.
 */
public interface QuestionData {
    String get(String key);

    Integer getInt(String key);

    Set<Integer> getIntSet(String key);

    List<Integer> getIntList(String key);

    List<String> getStringList(String key);
}
