package ru.geekbrains.repo;

import ru.geekbrains.model.Category;
import ru.geekbrains.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
