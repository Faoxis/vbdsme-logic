package ru.vbdsme.vbdsmelogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vbdsme.vbdsmelogic.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
