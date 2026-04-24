package ru.cyberc3dr.quiz.test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * История снимков состояния теста.
 * Хранит и управляет снимками для возможности отката.
 */
public final class TestHistory {

    private final Deque<TestSnapshot> history;

    public TestHistory() {
        this.history = new ArrayDeque<>();
    }

    public void save(TestSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("Snapshot cannot be null");
        }
        history.addLast(snapshot);
    }

    public TestSnapshot undo() {
        return history.pollLast();
    }

    public TestSnapshot peek() {
        return history.peekLast();
    }

    public void clear() {
        history.clear();
    }

    public int size() {
        return history.size();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
