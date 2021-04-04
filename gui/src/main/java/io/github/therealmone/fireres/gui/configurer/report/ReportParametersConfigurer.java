package io.github.therealmone.fireres.gui.configurer.report;

import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.preset.Preset;

public interface ReportParametersConfigurer<T extends ReportContainer<?, ?>> {

    void config(T reportContainer, Preset preset);

}
