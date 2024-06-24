package ru.practicum.adminApi.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class NewUserRequest {

    @NotNull
    @Email
    @Size(min = 6, max = 254)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

}
