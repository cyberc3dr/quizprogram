package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.factory.*;
import ru.cyberc3dr.quiz.io.FileTestLoader;
import ru.cyberc3dr.quiz.io.PlainTextParser;
import ru.cyberc3dr.quiz.scoring.PassFailStrategy;
import ru.cyberc3dr.quiz.scoring.PercGradingStrategy;

/**
 * Точка входа программы.
 */
public final class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        QuestionFactoryRegistry registry = QuestionFactoryRegistry.getInstance();

        registry.register("text", new TextQuestionFactory());
        registry.register("single", new SingleQuestionFactory());
        registry.register("multi", new MultiQuestionFactory());
        registry.register("ordered", new OrderedQuestionFactory());
        registry.register("ranged", new RangedQuestionFactory());

        FileTestLoader loader = new FileTestLoader("test.txt", new PlainTextParser());
        Test test = loader.load();

        int score = test.run();
        test.printScore(score);

        test.setStrategy(new PercGradingStrategy());
        test.printScore(score);

        test.setStrategy(new PassFailStrategy(60));
        test.printScore(score);
    }
}
