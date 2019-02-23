package ru.vbdsme.vbdsmelogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vbdsme.vbdsmelogic.domain.Item;
import ru.vbdsme.vbdsmelogic.service.item.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ItemController {
    public static final String URL = "/items";

    private ItemService itemService;

    @PostMapping(URL)
    public Item save(@Valid @RequestBody Item item) {
        return itemService.save(item);
    }

    @GetMapping(URL)
    public List<Item> get() {
        return itemService.getAll();
    }
}
