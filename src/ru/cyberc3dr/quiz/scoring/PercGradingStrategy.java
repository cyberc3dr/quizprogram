package ru.cyberc3dr.quiz.scoring;

import ru.cyberc3dr.quiz.Test;

public final class PercGradingStrategy implements GradingStrategy {

    @Override
    public void grade(Test test, int score) {
        int maxScore = test.getMaxScore();
        double percentage = (double) score / maxScore * 100;

        System.out.printf("Вы набрали %.2f%%.\n", percentage);
    }
}
