package ru.practicum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.practicum.common.statLog.StatisticInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final StatisticInterceptor statisticInterceptor;

    public WebConfig(StatisticInterceptor statisticInterceptor) {
        this.statisticInterceptor = statisticInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statisticInterceptor).addPathPatterns("/**");
    }

}
