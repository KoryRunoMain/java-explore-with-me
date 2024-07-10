package ru.practicum.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class NewCommentDto {

    @NotBlank
    @Size(min = 1, max = 1000)
    private String text;

}
