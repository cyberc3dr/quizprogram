package ru.cyberc3dr.quiz.tree;

import ru.cyberc3dr.quiz.answer.Answer;
import ru.cyberc3dr.quiz.test.TestRunContext;

import java.util.List;

/**
 * Класс являющийся листом, единицей секции вопросов.
 * <p>
 * Является контекстом для стратегий {@link Answer}
 */
public final class Question implements Leaf {
    private final String title;
    private final List<String> options;
    private final int score;
    private Answer answer;

    public Question(String title, List<String> options, int score, Answer answer) {
        this.title = title;
        this.options = options;
        this.score = score;
        this.answer = answer;
    }

    public int checkAnswer(String input) {
        return answer.check(input) ? score : 0;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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

    @Override
    public void run(TestRunContext context) {
        System.out.println("\n" + title);

        if (options.size() == 1) {
            System.out.println("Ans: " + options.getFirst());
        } else if (options.size() > 1) {
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
        }

        System.out.print("Введите ответ: ");
        String input = context.readLine().trim();
        int score = checkAnswer(input);

        context.getTest().addScore(score);
        System.out.println("Текущий счет: " + context.getTest().getCurrentScore());
    }

    @Override
    public int getMaxScore() {
        return score;
    }
}
