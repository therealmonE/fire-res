package io.github.therealmone.fireres.gui.preset;

import io.github.therealmone.fireres.core.config.ReportProperties;

public interface Preset {

    <T extends ReportProperties> T getProperties(Class<T> propertiesClass);

}
