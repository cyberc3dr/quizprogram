package ru.cyberc3dr.quiz.answer;

import ru.cyberc3dr.quiz.util.NumberStringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class MultiChoiceAnswer implements Answer {

    private final Set<Integer> correctValues;

    public MultiChoiceAnswer(Set<Integer> correctValues) {
        this.correctValues = correctValues;
    }

    public Set<Integer> getCorrectValues() {
        return correctValues;
    }

    @Override
    public boolean check(String input) {
        try {
            Set<Integer> ans = NumberStringUtils.parseNumberSet(input);

            return correctValues.equals(ans);
        } catch (NumberFormatException e) {
            System.out.println("Input must be a comma-separated list of numbers!");
        }

        return false;
    }
}
