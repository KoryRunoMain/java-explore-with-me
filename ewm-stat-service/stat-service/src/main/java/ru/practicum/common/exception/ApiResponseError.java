package ru.practicum.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponseError {

    private String status;
    private String reason;
    private String message;
    private String timestamp;

}
