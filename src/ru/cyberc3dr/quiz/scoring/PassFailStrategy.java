package ru.cyberc3dr.quiz.scoring;

public final class PassFailStrategy implements GradingStrategy {

    private final double passThreshold;

    public PassFailStrategy(double passThreshold) {
        this.passThreshold = passThreshold;
    }

    @Override
    public void grade(ru.cyberc3dr.quiz.Test test, int score) {
        int maxScore = test.getMaxScore();
        double percentage = (double) score / maxScore * 100;

        if (percentage >= passThreshold) {
            System.out.println("Поздравляем! Вы прошли тест.");
        } else {
            System.out.println("К сожалению, вы не прошли тест.");
        }
    }
}
