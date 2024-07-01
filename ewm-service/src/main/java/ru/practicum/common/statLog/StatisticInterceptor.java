package ru.practicum.common.statLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class StatisticInterceptor implements HandlerInterceptor {
    private final StatClientRequest clientRequest;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatisticInterceptor(StatClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            HitDto hitDto = HitDto.builder()
                    .app("ewm-service")
                    .uri(request.getRequestURI())
                    .ip(request.getRemoteAddr())
                    .timestamp(LocalDateTime.now().format(formatter))
                    .build();
            clientRequest.addHit(hitDto);
        } catch (Exception e) {
            log.info("Error sending hit: " + e.getMessage());
        }
        return true;
    }

}
