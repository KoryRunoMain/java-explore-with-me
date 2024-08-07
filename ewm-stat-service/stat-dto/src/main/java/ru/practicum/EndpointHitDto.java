package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
