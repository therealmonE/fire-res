package io.github.therealmone.fireres.gui.controller;

import io.github.therealmone.fireres.core.model.Sample;

public interface SampleContainer {

    Sample getSample();

    default void generateReports() {
    }

}
