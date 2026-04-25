package ru.cyberc3dr.quiz.state;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.test.Test;

/**
 * Состояние: тест в стадии прохождения.
 */
public final class ActiveState implements TestState {

    @Override
    public void reset(Test test) {
        test.setCurrentQuestionIndex(0);
        test.setCurrentScore(0);
        test.setState(new ReadyState());
    }

    @Override
    public boolean hasNextQuestion(Test test) {
        return test.getCurrentQuestionIndex() < test.getQuestions().size();
    }

    @Override
    public Question getNextQuestion(Test test) {
        if (!hasNextQuestion(test)) {
            test.setState(new CompletedState());
            throw new IllegalStateException("Нет больше вопросов. Тест завершен.");
        }
        return test.getQuestions().get(test.getCurrentQuestionIndex());
    }

    @Override
    public void advanceQuestion(Test test) {
        int newIndex = test.getCurrentQuestionIndex() + 1;
        test.setCurrentQuestionIndex(newIndex);

        if (newIndex >= test.getQuestions().size()) {
            test.setState(new CompletedState());
        }
    }

    @Override
    public void printScore(Test test) {
        System.out.println("Тест еще не завершен. Пройдите все вопросы для получения результата.");
    }
}
