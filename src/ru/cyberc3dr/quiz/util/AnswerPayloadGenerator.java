package ru.cyberc3dr.quiz.util;

import ru.cyberc3dr.quiz.io.QuizTemplate;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Генератор полезной нагрузки запроса.
 */
public final class AnswerPayloadGenerator {

    public static String createPayload(QuizTemplate template, int requestId) {
        int seed = ThreadLocalRandom.current().nextInt(1000, 9999);
        return template.getName() + "-attempt-" + requestId + "-seed-" + seed;
    }
}
