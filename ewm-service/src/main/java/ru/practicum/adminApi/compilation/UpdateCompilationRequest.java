package ru.practicum.adminApi.compilation;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateCompilationRequest {

    private List<Long> events;
    private boolean pinned;
    private String title;

}
