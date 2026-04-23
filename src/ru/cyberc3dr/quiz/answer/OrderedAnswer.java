package ru.cyberc3dr.quiz.answer;

import ru.cyberc3dr.quiz.util.NumberStringUtils;

import java.util.List;

public final class OrderedAnswer implements Answer {

    private final List<Integer> correctValues;

    public OrderedAnswer(List<Integer> correctValues) {
        this.correctValues = correctValues;
    }

    public List<Integer> getCorrectValues() {
        return correctValues;
    }

    @Override
    public boolean check(String input) {
        try {
            List<Integer> ans = NumberStringUtils.parseNumberList(input);

            return ans.equals(correctValues);
        } catch (NumberFormatException e) {
            System.out.println("Input must be a comma-separated list of numbers!");
        }

        return false;
    }
}
