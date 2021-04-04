package io.github.therealmone.fireres.gui.preset;

import io.github.therealmone.fireres.firemode.config.FireModeProperties;

public class Fan extends AbstractPreset {

    public Fan() {
        registerProperties(fireModeProperties());
    }

    private FireModeProperties fireModeProperties() {
        return FireModeProperties.builder()
                .showBounds(false)
                .showMeanTemperature(false)
                .temperaturesMaintaining(400)
                .build();
    }

    @Override
    public String getDescription() {
        return "Вентиляторы";
    }

}
