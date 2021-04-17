package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.core.config.ReportProperties;
import io.github.therealmone.fireres.gui.preset.Preset;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PresetService {

    List<Preset> getAvailablePresets();

    Preset getDefaultPreset();

    void savePreset(Boolean applyingByDefault, String description, Map<Class<? extends ReportProperties>, ReportProperties> properties) throws IOException;
}
