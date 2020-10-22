package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;

public class ThermocouplesMeanTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Тср";

    public ThermocouplesMeanTemperatureColumn(Integer index, ThermocoupleMeanTemperature thermocoupleMeanTemperature) {
        super(HEADER + index, thermocoupleMeanTemperature);
    }
}
