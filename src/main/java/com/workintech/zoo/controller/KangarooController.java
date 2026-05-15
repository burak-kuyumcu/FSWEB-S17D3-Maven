package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public Collection<Kangaroo> getAll() {
        return kangaroos.values();
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) {

        Kangaroo kangaroo = kangaroos.get(id);

        if (kangaroo == null) {
            throw new ZooException(
                    "Kangaroo with id " + id + " not found.",
                    HttpStatus.NOT_FOUND
            );
        }

        return kangaroo;
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {

        if (kangaroo.getId() == 0 || kangaroo.getName() == null) {
            throw new IllegalArgumentException("Invalid kangaroo data.");
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id,
                           @RequestBody Kangaroo kangaroo) {

        if (!kangaroos.containsKey(id)) {
            throw new ZooException(
                    "Kangaroo with id " + id + " not found.",
                    HttpStatus.NOT_FOUND
            );
        }

        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);

        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {

        Kangaroo deletedKangaroo = kangaroos.remove(id);

        if (deletedKangaroo == null) {
            throw new ZooException(
                    "Kangaroo with id " + id + " not found.",
                    HttpStatus.NOT_FOUND
            );
        }

        return deletedKangaroo;
    }
}