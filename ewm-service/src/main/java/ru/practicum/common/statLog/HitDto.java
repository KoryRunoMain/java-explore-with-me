package ru.practicum.common.statLog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class HitDto {

    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}
