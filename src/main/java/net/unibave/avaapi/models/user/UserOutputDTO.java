package net.unibave.avaapi.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOutputDTO {

    private Long id;
    private String name;
    private String email;

}
