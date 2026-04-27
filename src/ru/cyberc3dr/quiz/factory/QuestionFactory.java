package ru.cyberc3dr.quiz.factory;

import ru.cyberc3dr.quiz.tree.Question;
import ru.cyberc3dr.quiz.data.QuestionData;

/**
 * Общий интерфейс для фабрик Question
 */
public interface QuestionFactory {
    Question createQuestion(QuestionData data);
}
