package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.test.Test;

/**
 * Состояние: тест готов к прохождению.
 */
public final class ReadyState implements TestState {

    @Override
    public void reset(Test test) {
        System.out.println("Сбрасывать нечего, тест никто не выполнял.");
    }

    @Override
    public boolean hasNextQuestion(Test test) {
        return !test.getQuestions().isEmpty();
    }

    @Override
    public Question getNextQuestion(Test test) {
        test.setState(new ActiveState());
        return test.getQuestions().getFirst();
    }

    @Override
    public void advanceQuestion(Test test) {
        test.setState(new ActiveState());
        test.setCurrentQuestionIndex(1);
    }

    @Override
    public void printScore(Test test) {
        System.out.println("Тест еще не начат. Пройдите тест для получения результата.");
    }
}
