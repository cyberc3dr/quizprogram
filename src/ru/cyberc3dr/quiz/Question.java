package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.answer.Answer;
import ru.cyberc3dr.quiz.test.Test;

import java.util.List;

/**
 * Класс представляющий структуру вопроса.
 * Является контекстом для стратегий Answer.
 */
public final class Question {
    private final String title;
    private final List<String> options;
    private final int score;
    private Answer answer;

    public Question(String title, List<String> options, int score, Answer answer) {
        this.title = title;
        this.options = options;
        this.score = score;
        this.answer = answer;
    }

    public int checkAnswer(String input) {
        return answer.check(input) ? score : 0;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getScore() {
        return score;
    }

    public Answer getAnswer() {
        return answer;
    }
}
