package com.ankush.Blogvista.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min=4,message ="Username must be of atleast 4 Characters")
    private String name;

    @Email(message = "Your Email is not Valid")
    private String email;

    @NotEmpty
    @Size(min=4,max = 10,message = "password must be of minimum 4 and maximum 10 characters")
    private String password;

    @NotEmpty
    private String about;


}
