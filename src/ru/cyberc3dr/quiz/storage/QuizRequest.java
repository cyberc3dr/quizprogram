package ru.cyberc3dr.quiz.storage;

import ru.cyberc3dr.quiz.io.QuizTemplate;

/**
 * Запрос клиента к серверу.
 */
public final class QuizRequest {

    private final int requestId;
    private final String clientName;
    private final QuizTemplate template;
    private final String payload;
    private final boolean poisonPill;

    private QuizRequest(int requestId, String clientName, QuizTemplate template, String payload, boolean poisonPill) {
        this.requestId = requestId;
        this.clientName = clientName;
        this.template = template;
        this.payload = payload;
        this.poisonPill = poisonPill;
    }

    public static QuizRequest create(int requestId, String clientName, QuizTemplate template, String payload) {
        return new QuizRequest(requestId, clientName, template, payload, false);
    }

    public static QuizRequest poisonPill() {
        return new QuizRequest(-1, "system", null, "", true);
    }

    public int getRequestId() {
        return requestId;
    }

    public String getClientName() {
        return clientName;
    }

    public QuizTemplate getTemplate() {
        return template;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isPoisonPill() {
        return poisonPill;
    }
}
