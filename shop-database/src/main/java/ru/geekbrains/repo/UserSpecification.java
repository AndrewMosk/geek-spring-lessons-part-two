package ru.geekbrains.repo;

import ru.geekbrains.model.User;
import org.springframework.data.jpa.domain.Specification;


public final class UserSpecification {

    public static Specification<User> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<User> ageGreaterThanOrEqual(Integer age) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("age"), age);
    }

    public static Specification<User> ageLessThanOrEqual(Integer age) {

        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("age"), age);
    }

    public static Specification<User> findUserByName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");

    }
}
