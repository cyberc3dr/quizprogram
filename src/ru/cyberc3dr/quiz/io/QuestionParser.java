package ru.cyberc3dr.quiz.io;

import ru.cyberc3dr.quiz.data.QuestionData;

import java.util.List;

public interface QuestionParser<T> {
    List<QuestionData> parse(T source);
}
