package ru.cyberc3dr.quiz.client;

import ru.cyberc3dr.quiz.io.QuizTemplate;
import ru.cyberc3dr.quiz.server.QuizServer;
import ru.cyberc3dr.quiz.storage.QuizRequest;
import ru.cyberc3dr.quiz.util.AnswerPayloadGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Генератор запросов к серверу.
 */
public final class QuizClientGenerator implements Runnable {

    private final String clientName;
    private final int requestsToSend;
    private final long minDelayMillis;
    private final long maxDelayMillis;
    private final QuizServer server;
    private final QuizTemplate template;
    private final AtomicInteger requestCounter;

    public QuizClientGenerator(String clientName, int requestsToSend, long minDelayMillis, long maxDelayMillis, QuizServer server, QuizTemplate template, AtomicInteger requestCounter) {
        this.clientName = clientName;
        this.requestsToSend = requestsToSend;
        this.minDelayMillis = minDelayMillis;
        this.maxDelayMillis = maxDelayMillis;
        this.server = server;
        this.template = template;
        this.requestCounter = requestCounter;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void run() {
        for (int i = 0; i < requestsToSend; i++) {
            try {
                int requestId = requestCounter.incrementAndGet();
                String payload = AnswerPayloadGenerator.createPayload(template, requestId);
                QuizRequest request = QuizRequest.create(requestId, clientName, template, payload);
                server.submitRequest(request);
                Thread.sleep(randomDelay());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private long randomDelay() {
        if (maxDelayMillis <= minDelayMillis) {
            return minDelayMillis;
        }
        return ThreadLocalRandom.current().nextLong(minDelayMillis, maxDelayMillis + 1);
    }

    public static final class Builder {
        private String clientName;
        private int requestsToSend;
        private long minDelayMillis = 100;
        private long maxDelayMillis = 350;
        private QuizServer server;
        private QuizTemplate template;
        private AtomicInteger requestCounter;

        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public Builder requestsToSend(int requestsToSend) {
            this.requestsToSend = requestsToSend;
            return this;
        }

        public Builder minDelayMillis(long minDelayMillis) {
            this.minDelayMillis = minDelayMillis;
            return this;
        }

        public Builder maxDelayMillis(long maxDelayMillis) {
            this.maxDelayMillis = maxDelayMillis;
            return this;
        }

        public Builder server(QuizServer server) {
            this.server = server;
            return this;
        }

        public Builder template(QuizTemplate template) {
            this.template = template;
            return this;
        }

        public Builder requestCounter(AtomicInteger requestCounter) {
            this.requestCounter = requestCounter;
            return this;
        }

        public QuizClientGenerator build() {
            if (clientName == null || server == null || template == null || requestCounter == null) {
                throw new IllegalStateException("Не заданы обязательные поля генератора запросов");
            }
            if (requestsToSend < 0) {
                throw new IllegalStateException("Количество запросов не может быть отрицательным");
            }
            if (minDelayMillis < 0 || maxDelayMillis < 0 || maxDelayMillis < minDelayMillis) {
                throw new IllegalStateException("Некорректные значения задержки отправки");
            }
            return new QuizClientGenerator(
                    clientName,
                    requestsToSend,
                    minDelayMillis,
                    maxDelayMillis,
                    server,
                    template,
                    requestCounter
            );
        }
    }
}
