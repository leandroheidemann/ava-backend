package net.unibave.avaapi.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInputDTO implements Serializable {

    private String name;
    private String email;
    private String password;

}
