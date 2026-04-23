package ru.cyberc3dr.quiz.scoring;

import ru.cyberc3dr.quiz.Test;

public final class SumGradingStrategy implements GradingStrategy {

    @Override
    public void grade(Test test, int score) {
        System.out.println("Вы набрали " + score + " из " + test.getMaxScore() + " баллов.");
    }
}
