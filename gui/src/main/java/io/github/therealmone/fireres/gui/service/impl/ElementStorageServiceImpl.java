package io.github.therealmone.fireres.gui.service.impl;

import io.github.therealmone.fireres.gui.service.ElementStorageService;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ElementStorageServiceImpl implements ElementStorageService {

    private final Map<String, List<Object>> storage = new HashMap<>();

    @Override
    public void addById(String id, Object element) {
        storage.putIfAbsent(id, new ArrayList<>());
        storage.get(id).add(element);
    }

    @Override
    public List<Object> getAllById(String id) {
        return storage.getOrDefault(id, Collections.emptyList());
    }

    @Override
    public Optional<Object> getFirstById(String id) {
        val elements = getAllById(id);

        if (elements.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(elements.get(0));
        }
    }
}
