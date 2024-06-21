package ru.practicum;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EndpointHitDto {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}
