package ru.practicum.common.statLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class StatisticInterceptor implements HandlerInterceptor {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatClient statClient;

    public StatisticInterceptor(StatClient statClient) {
        this.statClient = statClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            EndpointHitDto hitDto = EndpointHitDto.builder()
                    .app("ewm-main-service")
                    .uri(request.getRequestURI())
                    .ip(request.getRemoteAddr())
                    .timestamp(LocalDateTime.now().format(formatter))
                    .build();
            statClient.addEndpointHit(hitDto);
        } catch (Exception e) {
            log.info("StatisticInterceptor: Error sending hit: " + e.getMessage());
        }
        return true;
    }

}
