package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.answer.Answer;

import java.util.List;
import java.util.Scanner;

/**
 * Класс представляющий структуру вопроса.
 * Является контекстом для стратегий Answer.
 * Также является листом в рамках {@link Test}
 */
public final class Question {
    private final String title;
    private final List<String> options;
    private final int score;
    private final Answer answer;

    public Question(String title, List<String> options, int score, Answer answer) {
        this.title = title;
        this.options = options;
        this.score = score;
        this.answer = answer;
    }

    public int ask(Scanner scanner) {
        System.out.println("\n" + title);

        if(options.size() == 1) {
            System.out.println("Ans: " + options.getFirst());
        } else if(options.size() > 1) {
            for(int i = 0; i < options.size(); i++) {
                System.out.println(i+1 + ". " + options.get(i));
            }
        }

        System.out.println("Введите ответ:");
        String input = scanner.nextLine();

        return answer.check(input) ? score : 0;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getScore() {
        return score;
    }

    public Answer getAnswer() {
        return answer;
    }
}
