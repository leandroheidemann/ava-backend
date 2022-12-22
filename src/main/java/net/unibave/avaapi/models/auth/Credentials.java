package net.unibave.avaapi.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credentials {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
