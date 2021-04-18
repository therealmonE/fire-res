package io.github.therealmone.fireres.gui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.ApplicationConfig;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.PresetService;
import lombok.val;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class PresetServiceImpl implements PresetService {

    @Inject
    private List<Preset> availablePresets;

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private ObjectMapper objectMapper;

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

    @Override
    public void savePreset(Preset preset) throws IOException {
        val path = applicationConfig.getCustomPresetsPath() + "/custom_preset_" + UUID.randomUUID() + ".json";

        try (val bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(objectMapper.writeValueAsString(preset));
            getAvailablePresets().add(preset);
        }
    }

}
