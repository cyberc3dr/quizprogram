package ru.cyberc3dr.quiz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.cyberc3dr.quiz.data.QuestionData;
import ru.cyberc3dr.quiz.io.FileTestLoader;
import ru.cyberc3dr.quiz.tree.Question;

import java.util.List;

@Aspect
@Component
public final class BeanLoggingAspect {

    @Around("execution(* ru.cyberc3dr.quiz.io.TestParser.parse(..))")
    public Object aroundParse(ProceedingJoinPoint joinPoint) throws Throwable {
        String source = (String) joinPoint.getArgs()[0];
        int lineCount = source.isBlank() ? 0 : source.split("\\R").length;
        System.out.println("[TestParser] Парсинг теста начат. Строк: " + lineCount);

        try {
            Object result = joinPoint.proceed();
            int parsedNodes = ((List<?>) result).size();
            System.out.println("[TestParser] Парсинг завершен. Корневых узлов: " + parsedNodes);
            return result;
        } catch (Throwable throwable) {
            System.out.println("[TestParser] Ошибка парсинга: " + throwable.getMessage());
            throw throwable;
        }
    }

    @Around("execution(* ru.cyberc3dr.quiz.io.TestLoader.load(..))")
    public Object aroundLoad(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String source = target instanceof FileTestLoader loader ? loader.getPath() : target.getClass().getSimpleName();
        System.out.println("[TestLoader] Загрузка теста. Источник: " + source);

        try {
            Object result = joinPoint.proceed();
            System.out.println("[TestLoader] Тест загружен успешно.");
            return result;
        } catch (Throwable throwable) {
            System.out.println("[TestLoader] Ошибка загрузки теста: " + throwable.getMessage());
            throw throwable;
        }
    }

    @AfterReturning(
            value = "execution(* ru.cyberc3dr.quiz.factory.QuestionFactory.createQuestion(..))"
                    + " && args(data)",
            returning = "createdQuestion",
            argNames = "joinPoint,data,createdQuestion"
    )
    public void afterQuestionCreation(JoinPoint joinPoint, QuestionData data, Question createdQuestion) {
        String factoryName = joinPoint.getTarget().getClass().getSimpleName();
        String questionType = data.get("QuestionType");
        String title = createdQuestion.getTitle();
        int optionsCount = createdQuestion.getOptions().size();
        int score = createdQuestion.getScore();
        String answerType = createdQuestion.getAnswer().getClass().getSimpleName();

        System.out.println(
                "[" + factoryName + "] Создан вопрос: type=" + questionType
                        + ", title=\"" + title + "\""
                        + ", options=" + optionsCount
                        + ", score=" + score
                        + ", answer=" + answerType
        );
    }

    @Before("execution(* ru.cyberc3dr.quiz.scoring.GradingStrategy.grade(int, int))")
    public void beforeGrade(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        int score = (int) args[0];
        int maxScore = (int) args[1];
        String details = target.toString();
        System.out.println("[Scoring] Оценивание: strategy=" + details + ", score=" + score + "/" + maxScore);
    }
}
