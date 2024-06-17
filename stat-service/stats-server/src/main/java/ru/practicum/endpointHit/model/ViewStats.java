package ru.practicum.endpointHit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ViewStats {

    private String app;
    private String uri;
    private Long hits;

}
