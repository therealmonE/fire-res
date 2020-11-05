package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.config.Coefficients;
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
import lombok.val;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class PointSequenceFactory {

    private final GenerationProperties generationProperties;

    public StandardTemperature standardTemperature() {
        return new StandardTempGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                generationProperties.getTime())
                .generate();
    }

    public FurnaceTemperature furnaceTemperature(StandardTemperature standardTemp) {
        return new FurnaceTempGenerator(
                generationProperties.getTime(),
                generationProperties.getTemperature().getEnvironmentTemperature(),
                standardTemp)
                .generate();
    }

    public MinAllowedTemperature minAllowedTemperature(StandardTemperature standardTemp) {
        return new MinAllowedTempGenerator(
                generationProperties.getTime(),
                new Coefficients(generationProperties.getTemperature().getMinAllowedTempCoefficients()),
                standardTemp)
                .generate();
    }

    public MaxAllowedTemperature maxAllowedTemperature(StandardTemperature standardTemp) {
        return new MaxAllowedTempGenerator(
                generationProperties.getTime(),
                new Coefficients(generationProperties.getTemperature().getMaxAllowedTempCoefficients()),
                standardTemp)
                .generate();
    }

    public ThermocoupleMeanTemperature thermocoupleMeanTemperature(Integer sampleNumber,
                                                                   MinAllowedTemperature minAllowedTemperature,
                                                                   MaxAllowedTemperature maxAllowedTemperature) {

        val sample = generationProperties.getSamples().get(sampleNumber);

        return new ThermocoupleMeanGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                generationProperties.getTime(),
                minAllowedTemperature,
                maxAllowedTemperature,
                new InterpolationPoints(sample.getInterpolationPoints()),
                sample.getRandomPoints().getEnrichWithRandomPoints(),
                sample.getRandomPoints().getNewPointChance())
                .generate();
    }

    public List<ThermocoupleTemperature> thermocouplesTemperatures(MinAllowedTemperature minAllowedTemperature,
                                                                   MaxAllowedTemperature maxAllowedTemperature,
                                                                   ThermocoupleMeanTemperature thermocoupleMeanTemperature,
                                                                   Integer sampleNumber) {

        val sample = generationProperties.getSamples().get(sampleNumber);

        return new ThermocouplesTempGenerator(
                generationProperties.getTime(),
                generationProperties.getTemperature().getEnvironmentTemperature(),
                thermocoupleMeanTemperature,
                minAllowedTemperature,
                maxAllowedTemperature,
                sample.getThermocoupleCount())
                .generate();
    }

}
