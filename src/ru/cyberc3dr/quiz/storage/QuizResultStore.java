package ru.cyberc3dr.quiz.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Потокобезопасное хранилище результатов.
 */
public final class QuizResultStore {

    private final List<QuizResult> results = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private int totalScore;

    public void add(QuizResult result) {
        lock.lock();
        try {
            results.add(result);
            totalScore += result.getMaxScore();
        } finally {
            lock.unlock();
        }
    }

    public List<QuizResult> snapshot() {
        lock.lock();
        try {
            return List.copyOf(results);
        } finally {
            lock.unlock();
        }
    }

    public double getAverageScore() {
        lock.lock();
        try {
            if (results.isEmpty()) {
                return 0.0;
            }
            return (double) totalScore / results.size();
        } finally {
            lock.unlock();
        }
    }
}
