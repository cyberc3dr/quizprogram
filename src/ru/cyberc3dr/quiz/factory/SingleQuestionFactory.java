package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.answer.SingleChoiceAnswer;
import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.List;

public final class SingleQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(QuestionData data) {
        String title = data.get("Title");
        List<String> options = data.getStringList("Options");
        int score = data.getInt("Score");
        int correct = data.getInt("Correct");

        return new Question(title, options, score, new SingleChoiceAnswer(correct));
    }
}
