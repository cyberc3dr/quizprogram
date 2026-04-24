package ru.cyberc3dr.quiz.test;

/**
 * Снимок состояния теста.
 * Хранит текущий балл и индекс вопроса для возможности отката.
 */
public final class TestSnapshot {

    private final int score;
    private final int currentQuestionIndex;

    public TestSnapshot(int score, int currentQuestionIndex) {
        this.score = score;
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
}
