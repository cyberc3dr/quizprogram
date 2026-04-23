package ru.cyberc3dr.quiz.answer;

public final class SingleChoiceAnswer implements Answer {

    private final int correct;

    public SingleChoiceAnswer(int correct) {
        this.correct = correct;
    }

    public int getCorrect() {
        return correct;
    }

    @Override
    public boolean check(String input) {
        try {
            int number = Integer.parseInt(input);

            return number == correct;
        } catch (NumberFormatException e) {
            System.out.println("Input must be a number!");
        }

        return false;
    }
}
