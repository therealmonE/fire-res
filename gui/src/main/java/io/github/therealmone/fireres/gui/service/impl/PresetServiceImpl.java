package io.github.therealmone.fireres.gui.service.impl;

import io.github.therealmone.fireres.gui.preset.Fan;
import io.github.therealmone.fireres.gui.preset.FireDoor;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.PresetService;

import java.util.List;

public class PresetServiceImpl implements PresetService {

    @Override
    public List<Preset> getAvailablePresets() {
        return List.of(
                new FireDoor(),
                new Fan()
        );
    }

}
