package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.HashMap;
import java.util.Map;

/**
 * Регистр фабрик {@link QuestionFactory}
 * <p>
 * Так же является синглтоном.
 */
public final class QuestionFactoryRegistry {

    private static final QuestionFactoryRegistry INSTANCE = new QuestionFactoryRegistry();

    private final Map<String, QuestionFactory> map = new HashMap<>();

    private QuestionFactoryRegistry() {
        // приватный конструктор
    }

    public static QuestionFactoryRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Ининиализация дефолтных фабрик
     */
    public static void initDefaults() {
        QuestionFactoryRegistry registry = getInstance();

        registry.register("text", new TextQuestionFactory());
        registry.register("single", new SingleQuestionFactory());
        registry.register("multi", new MultiQuestionFactory());
        registry.register("ordered", new OrderedQuestionFactory());
        registry.register("ranged", new RangedQuestionFactory());
    }

    public void register(String type, QuestionFactory factory) {
        map.put(type, factory);
    }

    public Question create(QuestionData data) {
        QuestionFactory factory = map.get(data.get("QuestionType"));

        if(factory == null) {
            throw new IllegalArgumentException("Неизвестный тип вопроса: " + data.get("QuestionType"));
        }

        return factory.createQuestion(data);
    }
}
