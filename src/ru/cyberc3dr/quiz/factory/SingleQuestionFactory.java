package ru.cyberc3dr.quiz.factory;

import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.answer.SingleChoiceAnswer;
import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.tree.Question;

import java.util.List;

@Component("single")
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
