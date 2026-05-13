package ru.cyberc3dr.quiz.answer;

import ru.cyberc3dr.quiz.util.NumberStringUtils;

import java.util.Set;

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
            System.out.println("Ввод должен быть списком чисел через запятую!");
        }

        return false;
    }
}
