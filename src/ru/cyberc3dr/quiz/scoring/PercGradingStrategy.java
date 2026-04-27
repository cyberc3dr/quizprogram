package ru.cyberc3dr.quiz.scoring;

public final class PercGradingStrategy implements GradingStrategy {

    @Override
    public void grade(int score, int maxScore) {
        double percentage = (double) score / maxScore * 100;

        System.out.printf("Вы набрали %.2f%%.\n", percentage);
    }
}
