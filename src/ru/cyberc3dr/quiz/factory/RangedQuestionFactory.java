package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.Question;
import ru.cyberc3dr.quiz.answer.RangedAnswer;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.List;

public final class RangedQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(QuestionData data) {
        String title = data.get("Title");
        List<String> options = data.getStringList("Options");
        int score = data.getInt("Score");
        int min = data.getInt("Min");
        int max = data.getInt("Max");

        return new Question(title, options, score, new RangedAnswer(min, max));
    }
}
