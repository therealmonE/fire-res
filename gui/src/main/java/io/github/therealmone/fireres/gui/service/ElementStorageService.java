package io.github.therealmone.fireres.gui.service;

import java.util.List;
import java.util.Optional;

public interface ElementStorageService {

    String SAMPLE_TAB = "sampleTab";
    String SAMPLES_TAB_PANE = "samplesTabPane";

    void addById(String id, Object element);

    List<Object> getAllById(String id);

    Optional<Object> getFirstById(String id);

}
