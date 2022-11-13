package net.unibave.avaapi.mappers;

import net.unibave.avaapi.entities.User;
import net.unibave.avaapi.models.user.UserInputDTO;
import net.unibave.avaapi.models.user.UserOutputDTO;
import net.unibave.avaapi.models.user.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({})
    UserOutputDTO toOutput(User user);

    @Mappings({})
    User toEntity(UserInputDTO userInputDTO);

    @Mappings({})
    User toEntity(UserUpdateDTO userInputDTO, User user);

}
