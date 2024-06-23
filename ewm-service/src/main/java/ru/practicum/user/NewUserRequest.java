package ru.practicum.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class NewUserRequest {

    @Email
    @Size(min = 6, max = 254)
    private String email;

    @Size(min = 2, max = 250)
    private String name;

}
