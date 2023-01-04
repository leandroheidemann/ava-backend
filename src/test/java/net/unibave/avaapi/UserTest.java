package net.unibave.avaapi;

import net.unibave.avaapi.models.user.UserInputDTO;
import net.unibave.avaapi.models.user.UserOutputDTO;
import net.unibave.avaapi.models.user.UserUpdateDTO;
import net.unibave.avaapi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    void testUser() {
        UserInputDTO userInput = new UserInputDTO("User Test", "user@test.com", "123456");
        UserOutputDTO user = userService.save(userInput);
        Assertions.assertNotNull(user);

        user = userService.findById(user.getId());
        Assertions.assertNotNull(user);

        UserUpdateDTO userUpdate = new UserUpdateDTO("User Test 2");
        user = userService.update(user.getId(), userUpdate);
        Assertions.assertEquals(userUpdate.getName(), user.getName());

        userService.delete(user.getId());
        UserOutputDTO userOutput = user;
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(userOutput.getId()));
    }
}
