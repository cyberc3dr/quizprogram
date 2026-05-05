package ru.cyberc3dr.quiz.factory;

import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.answer.MultiChoiceAnswer;
import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.tree.Question;

import java.util.List;

@Component("multi")
public final class MultiQuestionFactory implements QuestionFactory {
    @Override
    public Question createQuestion(QuestionData data) {
        String title = data.get("Title");
        List<String> options = data.getStringList("Options");
        int score = data.getInt("Score");

        return new Question(title, options, score, new MultiChoiceAnswer(data.getIntSet("Correct")));
    }
}
