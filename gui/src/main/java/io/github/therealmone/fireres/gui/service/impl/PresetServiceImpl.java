package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.PresetService;

import java.util.List;

public class PresetServiceImpl implements PresetService {

    @Inject
    private List<Preset> availablePresets;

    @Override
    public List<Preset> getAvailablePresets() {
        return availablePresets;
    }

    @Override
    public Preset getDefaultPreset() {
        return availablePresets.stream()
                .filter(Preset::getApplyingByDefault)
                .findFirst()
                .orElseThrow();
    }

}
