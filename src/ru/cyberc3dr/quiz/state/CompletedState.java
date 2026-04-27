package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.test.Test;

/**
 * Состояние: тест завершен.
 */
public final class CompletedState implements TestState {

    @Override
    public void reset(Test test) {
        test.setCurrentQuestionIndex(0);
        test.setCurrentScore(0);
        test.setState(new ReadyState());
    }

    @Override
    public boolean hasNextQuestion(Test test) {
        return false;
    }

    @Override
    public Question getNextQuestion(Test test) {
        throw new IllegalStateException("Тест завершен. Используйте reset() для повторного прохождения.");
    }

    @Override
    public void advanceQuestion(Test test) {
        throw new IllegalStateException("Тест завершен. Используйте reset() для повторного прохождения.");
    }

    @Override
    public void printScore(Test test) {
        test.getStrategy().grade(test.getCurrentScore(), test.getMaxScore());
    }
}
