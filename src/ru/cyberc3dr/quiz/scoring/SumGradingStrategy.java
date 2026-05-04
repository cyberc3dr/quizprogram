package ru.cyberc3dr.quiz.scoring;

public final class SumGradingStrategy implements GradingStrategy {

    @Override
    public void grade(int score, int maxScore) {
        System.out.println("Вы набрали " + score + " из " + maxScore + " баллов.");
    }

    @Override
    public String toString() {
        return "SumGradingStrategy{}";
    }
}
