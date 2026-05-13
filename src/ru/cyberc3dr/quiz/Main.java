package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.client.QuizClientGenerator;
import ru.cyberc3dr.quiz.factory.QuestionFactoryRegistry;
import ru.cyberc3dr.quiz.io.QuizTemplate;
import ru.cyberc3dr.quiz.io.QuizTemplateLoader;
import ru.cyberc3dr.quiz.server.QuizServer;
import ru.cyberc3dr.quiz.storage.QuizResult;
import ru.cyberc3dr.quiz.storage.QuizResultStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Точка входа ЛР3: генераторы запросов + общая очередь + исполнители.
 */
public final class Main {

    public static void main(String[] args) {
        QuestionFactoryRegistry.initDefaults();

        String quizPath = args.length > 0 ? args[0] : "test.txt";
        int clientsCount = args.length > 1 ? Integer.parseInt(args[1]) : 4;
        int requestsPerClient = args.length > 2 ? Integer.parseInt(args[2]) : 10;
        int processorCount = args.length > 3 ? Integer.parseInt(args[3]) : 2;

        QuizTemplate template = QuizTemplateLoader.loadFromPath(quizPath);
        QuizResultStore resultStore = new QuizResultStore();
        QuizServer server = new QuizServer(processorCount, resultStore);

        server.start();

        AtomicInteger requestCounter = new AtomicInteger(0);
        List<Thread> clientThreads = new ArrayList<>();

        for (int i = 1; i <= clientsCount; i++) {
            QuizClientGenerator clientGenerator = QuizClientGenerator.builder()
                    .clientName("client-" + i)
                    .requestsToSend(requestsPerClient)
                    .minDelayMillis(100)
                    .maxDelayMillis(350)
                    .server(server)
                    .template(template)
                    .requestCounter(requestCounter)
                    .build();

            Thread thread = new Thread(clientGenerator, "generator-" + i);
            clientThreads.add(thread);
            thread.start();
        }

        try {
            for (Thread clientThread : clientThreads) {
                clientThread.join();
            }
            server.stopProcessors();
            server.awaitCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Работа была прервана", e);
        }

        printResults(resultStore);
    }

    private static void printResults(QuizResultStore resultStore) {
        List<QuizResult> results = resultStore.snapshot();
        System.out.println("=== Клиент-серверная симуляция завершена ===");
        System.out.println("Обработано запросов: " + results.size());
        System.out.println("Средний максимальный счет: " + String.format(Locale.US, "%.2f", resultStore.getAverageScore()));

        for (QuizResult result : results) {
            System.out.println(
                    "#" + result.getRequestId()
                            + " client=" + result.getClientName()
                            + " processor=" + result.getProcessorName()
                            + " template=" + result.getTemplateName()
                            + " maxScore=" + result.getMaxScore()
                            + " timeMs=" + result.getProcessingMillis()
            );
        }
    }
}
