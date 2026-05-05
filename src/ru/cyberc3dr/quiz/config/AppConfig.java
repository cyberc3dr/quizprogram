package ru.cyberc3dr.quiz.config;

import org.springframework.context.annotation.*;
import ru.cyberc3dr.quiz.scoring.GradingStrategy;
import ru.cyberc3dr.quiz.scoring.PassFailStrategy;
import ru.cyberc3dr.quiz.scoring.PercGradingStrategy;
import ru.cyberc3dr.quiz.scoring.SumGradingStrategy;

@Configuration
@ComponentScan(basePackages = "ru.cyberc3dr.quiz")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public GradingStrategy sumGradingStrategy() {
        return new SumGradingStrategy();
    }

    @Bean
    @Primary
    public GradingStrategy percGradingStrategy() {
        return new PercGradingStrategy();
    }

    @Bean
    @Scope("prototype")
    public GradingStrategy passFailStrategy(double passThreshold) {
        return new PassFailStrategy(passThreshold);
    }
}
