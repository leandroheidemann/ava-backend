package net.unibave.avaapi.services;

import net.unibave.avaapi.mappers.UserMapper;
import net.unibave.avaapi.models.user.UserInputDTO;
import net.unibave.avaapi.models.user.UserOutputDTO;
import net.unibave.avaapi.models.user.UserUpdateDTO;
import net.unibave.avaapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserOutputDTO> findALl() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutput)
                .collect(Collectors.toList());
    }

    public UserOutputDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toOutput)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserOutputDTO save(UserInputDTO data) {
        var user = mapper.toEntity(data);

        user = repository.save(user);

        return mapper.toOutput(user);
    }

    @Transactional
    public UserOutputDTO update(Long id, UserUpdateDTO data) {
        var user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user = mapper.toEntity(data, user);

        user = repository.save(user);

        return mapper.toOutput(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
