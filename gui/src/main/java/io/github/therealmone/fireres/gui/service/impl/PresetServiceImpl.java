package io.github.therealmone.fireres.gui.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.PresetService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Override
    public void savePreset(Preset preset, String path) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
        bufferedWriter.write(new ObjectMapper().writeValueAsString(preset));
        bufferedWriter.close();
    }

}
