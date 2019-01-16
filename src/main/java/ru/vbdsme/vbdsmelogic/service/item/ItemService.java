package ru.vbdsme.vbdsmelogic.service.item;

import ru.vbdsme.vbdsmelogic.domain.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);
    List<Item> getAll();
}
