package ru.cyberc3dr.quiz.server;

import ru.cyberc3dr.quiz.storage.QuizRequest;
import ru.cyberc3dr.quiz.storage.QuizResultStore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Сервер: принимает запросы, ставит их в очередь и запускает исполнителей.
 */
public final class QuizServer {

    private final BlockingQueue<QuizRequest> queue = new LinkedBlockingQueue<>();
    private final List<Thread> processors = new ArrayList<>();
    private final QuizResultStore resultStore;

    public QuizServer(int processorCount, QuizResultStore resultStore) {
        if (processorCount <= 0) {
            throw new IllegalArgumentException("Количество исполнителей должно быть больше нуля");
        }

        this.resultStore = resultStore;

        for (int i = 1; i <= processorCount; i++) {
            Thread thread = new Thread(
                    new QuizRequestProcessor(queue, resultStore),
                    "processor-" + i
            );
            processors.add(thread);
        }
    }

    public void start() {
        for (Thread processor : processors) {
            processor.start();
        }
    }

    public void submitRequest(QuizRequest request) throws InterruptedException {
        queue.put(request);
    }

    public void stopProcessors() throws InterruptedException {
        for (int i = 0; i < processors.size(); i++) {
            queue.put(QuizRequest.poisonPill());
        }
    }

    public void awaitCompletion() throws InterruptedException {
        for (Thread processor : processors) {
            processor.join();
        }
    }

    public QuizResultStore getResultStore() {
        return resultStore;
    }
}
