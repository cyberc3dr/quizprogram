package ru.cyberc3dr.quiz.test;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;
import ru.cyberc3dr.quiz.state.ReadyState;
import ru.cyberc3dr.quiz.state.TestState;

import java.util.List;

/**
 * Тест состоящий из вопросов.
 * Контекст для стратегий оценки.
 * Является Originator для снимка.
 * Использует паттерн State для управления состояниями теста.
 */
public final class Test {

    private final List<Question> questions;
    private GradingStrategy strategy = new SumGradingStrategy();
    private TestState state;
    private int currentQuestionIndex = 0;
    private int currentScore = 0;

    public Test(List<Question> questions) {
        this.questions = questions;
        this.state = new ReadyState();
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

    public TestState getState() {
        return state;
    }

    public void setState(TestState state) {
        this.state = state;
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

    public void reset() {
        state.reset(this);
    }

    public boolean hasNextQuestion() {
        return state.hasNextQuestion(this);
    }

    public Question getNextQuestion() {
        return state.getNextQuestion(this);
    }

    public void advanceQuestion() {
        state.advanceQuestion(this);
    }

    public void printScore() {
        state.printScore(this);
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
