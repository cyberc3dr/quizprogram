package ru.cyberc3dr.quiz.answer;

public final class RangedAnswer implements Answer {

    private final int min, max;

    public RangedAnswer(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public boolean check(String input) {
        try {
            int number = Integer.parseInt(input);

            return number >= min && number <= max;
        } catch (NumberFormatException e) {
            System.out.println("Input must be a number!");
        }

        return false;
    }
}
