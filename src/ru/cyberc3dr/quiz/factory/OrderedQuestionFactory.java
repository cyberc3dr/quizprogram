package ru.cyberc3dr.quiz.factory;

import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.answer.OrderedAnswer;
import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.tree.Question;

import java.util.List;

@Component("ordered")
public final class OrderedQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(QuestionData data) {
        String title = data.get("Title");
        List<String> options = data.getStringList("Options");
        int score = data.getInt("Score");

        return new Question(title, options, score, new OrderedAnswer(data.getIntList("Correct")));
    }
}
