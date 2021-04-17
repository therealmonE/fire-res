package io.github.therealmone.fireres.gui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.ReportProperties;
import io.github.therealmone.fireres.gui.ApplicationConfig;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.PresetService;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PresetServiceImpl implements PresetService {

    @Inject
    private List<Preset> availablePresets;

    @Inject
    private ApplicationConfig applicationConfig;

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
    public void savePreset(Boolean applyingByDefault, String description, Map<Class<? extends ReportProperties>, ReportProperties> properties) throws IOException {
        val preset = Preset.builder()
                .applyingByDefault(applyingByDefault)
                .description(description)
                .properties(properties).build();
        val path = applicationConfig.getCustomPresetsPath() + "/custom_preset_" + UUID.randomUUID() + ".json";

        try (val bufferedWriter = new BufferedWriter(new FileWriter(new File(path)))) {
            val objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            bufferedWriter.write(objectMapper.writeValueAsString(preset));
            getAvailablePresets().add(preset);
        }
    }

}
