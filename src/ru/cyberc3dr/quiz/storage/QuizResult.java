package ru.cyberc3dr.quiz.storage;

/**
 * Результат обработки одного запроса.
 */
public final class QuizResult {

    private final int requestId;
    private final String clientName;
    private final String processorName;
    private final String templateName;
    private final String payload;
    private final int maxScore;
    private final long processingMillis;

    public QuizResult(int requestId, String clientName, String processorName, String templateName, String payload, int maxScore, long processingMillis) {
        this.requestId = requestId;
        this.clientName = clientName;
        this.processorName = processorName;
        this.templateName = templateName;
        this.payload = payload;
        this.maxScore = maxScore;
        this.processingMillis = processingMillis;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getRequestId() {
        return requestId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getProcessorName() {
        return processorName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getPayload() {
        return payload;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public long getProcessingMillis() {
        return processingMillis;
    }

    public static final class Builder {

        private int requestId;
        private String clientName;
        private String processorName;
        private String templateName;
        private String payload = "";
        private int maxScore;
        private long processingMillis;

        public Builder requestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public Builder processorName(String processorName) {
            this.processorName = processorName;
            return this;
        }

        public Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder maxScore(int maxScore) {
            this.maxScore = maxScore;
            return this;
        }

        public Builder processingMillis(long processingMillis) {
            this.processingMillis = processingMillis;
            return this;
        }

        public QuizResult build() {
            if (clientName == null || processorName == null || templateName == null) {
                throw new IllegalStateException("Не заданы обязательные поля результата");
            }
            return new QuizResult(
                    requestId,
                    clientName,
                    processorName,
                    templateName,
                    payload,
                    maxScore,
                    processingMillis
            );
        }
    }
}
