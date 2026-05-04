package ru.cyberc3dr.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.cyberc3dr.quiz.config.AppConfig;
import ru.cyberc3dr.quiz.io.FileTestLoader;
import ru.cyberc3dr.quiz.io.TestLoader;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.PassFailStrategy;
import ru.cyberc3dr.quiz.scoring.PercGradingStrategy;
import ru.cyberc3dr.quiz.test.Test;

import java.util.Scanner;

/**
 * Точка входа программы.
 */
public final class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите имя файла для загрузки теста: ");
            String filename = scanner.nextLine();

            TestLoader loader = context.getBean(TestLoader.class, filename);

            Test test = loader.load();

            test.reset();
            test.start(scanner);
            test.printScore();

            test.setStrategy(context.getBean("percGradingStrategy", GradingStrategy.class));
            test.printScore();

            test.setStrategy((GradingStrategy) context.getBean("passFailStrategy", 60.0));
            test.printScore();
        }
    }
}
