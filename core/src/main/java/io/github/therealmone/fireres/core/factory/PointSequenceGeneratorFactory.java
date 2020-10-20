package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.MultiplePointSequencesGenerator;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.generator.impl.FurnaceTempGenerator;
import io.github.therealmone.fireres.core.generator.impl.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.core.generator.impl.MinAllowedTempGenerator;
import io.github.therealmone.fireres.core.generator.impl.StandardTempGenerator;
import io.github.therealmone.fireres.core.generator.impl.ThermocoupleMeanGenerator;
import io.github.therealmone.fireres.core.generator.impl.ThermocouplesTempGenerator;
import io.github.therealmone.fireres.core.model.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class PointSequenceGeneratorFactory {

    private final GenerationProperties generationProperties;

    public PointSequenceGenerator<StandardTemperature> standardTempGenerator() {
        return new StandardTempGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                generationProperties.getTime());
    }

    public PointSequenceGenerator<FurnaceTemperature> furnaceTempGenerator(StandardTemperature standardTemp) {
        return new FurnaceTempGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                standardTemp);
    }

    public PointSequenceGenerator<MinAllowedTemperature> minAllowedTempGenerator(StandardTemperature standardTemp) {
        return new MinAllowedTempGenerator(
                generationProperties.getTemperature().getMinAllowedTempCoefficients(),
                standardTemp);
    }

    public PointSequenceGenerator<MaxAllowedTemperature> maxAllowedTempGenerator(StandardTemperature standardTemp) {
        return new MaxAllowedTempGenerator(
                generationProperties.getTemperature().getMaxAllowedTempCoefficients(),
                standardTemp);
    }

    public PointSequenceGenerator<ThermocoupleMeanTemperature> thermocoupleMeanGenerator() {
        return new ThermocoupleMeanGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                generationProperties.getTime(),
                generationProperties.getInterpolation().getInterpolationPoints(),
                generationProperties.getInterpolation().getInterpolationMethod(),
                generationProperties.getRandomPoints().getEnrichWithRandomPoints(),
                generationProperties.getRandomPoints().getNewPointChance());
    }

    public MultiplePointSequencesGenerator<ThermocoupleTemperature> thermocouplesTempGenerator(MinAllowedTemperature minAllowedTemperature,
                                                                                               MaxAllowedTemperature maxAllowedTemperature,
                                                                                               ThermocoupleMeanTemperature thermocoupleMeanTemperature) {
        return new ThermocouplesTempGenerator(
                generationProperties.getTime(),
                thermocoupleMeanTemperature,
                minAllowedTemperature,
                maxAllowedTemperature,
                generationProperties.getThermocoupleCount());
    }

}
