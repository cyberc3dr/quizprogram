package ru.cyberc3dr.quiz.server;

import ru.cyberc3dr.quiz.storage.QuizRequest;
import ru.cyberc3dr.quiz.storage.QuizResult;
import ru.cyberc3dr.quiz.storage.QuizResultStore;
import ru.cyberc3dr.quiz.io.FileTestLoader;
import ru.cyberc3dr.quiz.io.PlainTestParser;
import ru.cyberc3dr.quiz.test.Test;

import java.util.concurrent.BlockingQueue;

/**w
 * Исполнитель, который забирает запросы из очереди и обрабатывает их.
 */
public final class QuizRequestProcessor implements Runnable {

    private final BlockingQueue<QuizRequest> queue;
    private final QuizResultStore resultStore;

    public QuizRequestProcessor(BlockingQueue<QuizRequest> queue, QuizResultStore resultStore) {
        this.queue = queue;
        this.resultStore = resultStore;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                QuizRequest request = queue.take();
                if (request.isPoisonPill()) {
                    System.out.println(Thread.currentThread().getName() + " получил poison pill, выключение...");
                    return;
                }
                process(request);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void process(QuizRequest request) {
        long startedAt = System.nanoTime();
        FileTestLoader loader = new FileTestLoader(request.getTemplate().getPath(), PlainTestParser.getInstance());
        Test test = loader.load();
        if(test == null) return;
        int maxScore = test.getMaxScore();
        long processingMillis = (System.nanoTime() - startedAt) / 1_000_000;

        QuizResult result = QuizResult.builder()
                .requestId(request.getRequestId())
                .clientName(request.getClientName())
                .processorName(Thread.currentThread().getName())
                .templateName(request.getTemplate().getName())
                .payload(request.getPayload())
                .maxScore(maxScore)
                .processingMillis(processingMillis)
                .build();

        resultStore.add(result);
    }
}
