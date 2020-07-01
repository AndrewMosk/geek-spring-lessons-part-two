package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Role;
import ru.geekbrains.repo.RoleRepository;


import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(Role role) {
        repository.save(role);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findById(long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
