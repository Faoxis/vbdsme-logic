package ru.vbdsme.vbdsmelogic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vbdsme.vbdsmelogic.domain.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
}

