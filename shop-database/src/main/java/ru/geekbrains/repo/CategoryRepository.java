package ru.geekbrains.repo;

import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "with recursive cte (id, name, parent_id) as (\n" +
            "  select     id,\n" +
            "             name,\n" +
            "             parent_id\n" +
            "  from       categories\n" +
            "  where      id = ?1" +
            "  union all\n" +
            "  select     c.id,\n" +
            "             c.name,\n" +
            "             c.parent_id\n" +
            "  from       categories c\n" +
            "  inner join cte\n" +
            "          on c.id = cte.parent_id\n" +
            ")\n" +
            "select * from cte order by id;", nativeQuery = true)
    List<Category> findCategoryWithParents(Long id);

    Category findByName(String name);
}
