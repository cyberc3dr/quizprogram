package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.HashMap;
import java.util.Map;

/**
 * Регистр фабрик (необходим для парсинга из файла)
 * Так же является синглтоном.
 */
public final class QuestionFactoryRegistry {

    private static final QuestionFactoryRegistry INSTANCE = new QuestionFactoryRegistry();

    private final Map<String, QuestionFactory> map = new HashMap<>();

    private QuestionFactoryRegistry() {

    }

    public static QuestionFactoryRegistry getInstance() {
        return INSTANCE;
    }

    public void register(String type, QuestionFactory factory) {
        map.put(type, factory);
    }

    public Question create(QuestionData data) {
        QuestionFactory factory = map.get(data.get("QuestionType"));

        if(factory == null) {
            throw new IllegalArgumentException("Unknown question type: " + data.get("QuestionType"));
        }

        return factory.createQuestion(data);
    }
}
