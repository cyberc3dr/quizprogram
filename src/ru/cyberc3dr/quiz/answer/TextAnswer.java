package ru.cyberc3dr.quiz.answer;

public final class TextAnswer implements Answer {

    private final String correct;

    public TextAnswer(String correct) {
        this.correct = correct;
    }

    public String getCorrect() {
        return correct;
    }

    @Override
    public boolean check(String input) {
        return correct.equalsIgnoreCase(input);
    }
}
