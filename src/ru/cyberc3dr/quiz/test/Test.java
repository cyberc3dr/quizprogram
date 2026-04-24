package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;

import java.util.List;

/**
 * Тест состоящий из вопросов.
 * Является композитом для объектов типа {@link Question}.
 * Контекст для стратегий оценки.
 * Является Originator для снимка.
 */
public final class Test {

    private final List<Question> questions;
    private GradingStrategy strategy = new SumGradingStrategy();
    private int currentQuestionIndex = 0;
    private int currentScore = 0;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getMaxScore() {
        return questions.stream()
                .mapToInt(Question::getScore)
                .sum();
    }

    public GradingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public void addScore(int score) {
        this.currentScore += score;
    }

    public void advanceQuestion() {
        this.currentQuestionIndex++;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new IllegalStateException("No more questions");
        }
        return questions.get(currentQuestionIndex);
    }

    public void reset() {
        currentQuestionIndex = 0;
        currentScore = 0;
    }

    public void printScore() {
        strategy.grade(this, currentScore);
    }

    public TestSnapshot createSnapshot() {
        return new TestSnapshot(currentScore, currentQuestionIndex);
    }

    public void restoreFromSnapshot(TestSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("Snapshot cannot be null");
        }
        this.currentScore = snapshot.getScore();
        this.currentQuestionIndex = snapshot.getCurrentQuestionIndex();
    }
}
