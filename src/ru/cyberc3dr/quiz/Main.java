package ru.cyberc3dr.quiz;

import ru.cyberc3dr.quiz.factory.*;
import ru.cyberc3dr.quiz.test.Test;
import ru.cyberc3dr.quiz.util.ConsoleHandler;
import ru.cyberc3dr.quiz.io.FileTestLoader;
import ru.cyberc3dr.quiz.io.PlainTextParser;
import ru.cyberc3dr.quiz.scoring.PassFailStrategy;
import ru.cyberc3dr.quiz.scoring.PercGradingStrategy;

import java.util.Scanner;

/**
 * Точка входа программы.
 */
public final class Main {

    public static void main(String[] args) {
        QuestionFactoryRegistry registry = QuestionFactoryRegistry.getInstance();

        registry.register("text", new TextQuestionFactory());
        registry.register("single", new SingleQuestionFactory());
        registry.register("multi", new MultiQuestionFactory());
        registry.register("ordered", new OrderedQuestionFactory());
        registry.register("ranged", new RangedQuestionFactory());

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя файла для загрузки теста: ");
        String filename = scanner.nextLine();

        FileTestLoader loader = new FileTestLoader(filename, new PlainTextParser());
        Test test = loader.load();

        try(var handler = new ConsoleHandler(scanner)) {
            handler.runTest(test);
        }

        test.setStrategy(new PercGradingStrategy());
        test.printScore();

        test.setStrategy(new PassFailStrategy(60));
        test.printScore();
    }
}

