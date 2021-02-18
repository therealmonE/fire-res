package io.github.therealmone.fireres.gui.controller;

import io.github.therealmone.fireres.core.config.ReportProperties;
import io.github.therealmone.fireres.core.model.Report;

public interface ReportContainer<P extends ReportProperties, R extends Report<P>> extends SampleContainer {

    R getReport();

    default void createReport() {
    }

}
