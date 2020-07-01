package ru.geekbrains.repo;

import ru.geekbrains.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByName(String name);

    List<User> findByAgeGreaterThanEqual(Integer age);

    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    List<User> findByAgeLessThanEqual(Integer age);

    Optional<User> findUserByName(String username);
}
