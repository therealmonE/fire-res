package io.github.therealmone.fireres.gui.controller;

import io.github.therealmone.fireres.core.model.Report;

public interface ReportContainer extends SampleContainer {

    Report getReport();

    default void createReport() {
    }

}
