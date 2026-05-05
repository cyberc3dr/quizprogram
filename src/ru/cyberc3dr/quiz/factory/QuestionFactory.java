package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.tree.Question;

/**
 * Общий интерфейс для фабрик Question
 */
public interface QuestionFactory {
    Question createQuestion(QuestionData data);
}
