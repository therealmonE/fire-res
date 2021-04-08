package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.preset.Preset;

import java.util.List;

public interface PresetService {

    List<Preset> getAvailablePresets();

    Preset getDefaultPreset();

}
