package net.unibave.avaapi.controllers;

import net.unibave.avaapi.models.user.UserInputDTO;
import net.unibave.avaapi.models.user.UserOutputDTO;
import net.unibave.avaapi.models.user.UserUpdateDTO;
import net.unibave.avaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> findAll() {
        return ResponseEntity.ok(service.findALl());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> save(@RequestBody @Valid UserInputDTO data) {
        return ResponseEntity.ok(service.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDTO> update(@RequestBody @Valid UserUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
