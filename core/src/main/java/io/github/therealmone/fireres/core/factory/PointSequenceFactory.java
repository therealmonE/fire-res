package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.Coefficients;
import io.github.therealmone.fireres.core.generator.impl.*;
import io.github.therealmone.fireres.core.generator.common.MeanFunctionGenerator;
import io.github.therealmone.fireres.core.generator.common.MeanChildFunctionsGenerator;
import io.github.therealmone.fireres.core.model.firemode.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.firemode.ThermocoupleTemperature;
import io.github.therealmone.fireres.core.model.pressure.SamplePressure;
import io.github.therealmone.fireres.core.model.sequence.IntegerPointSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;


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

        val function = new MeanFunctionGenerator(
                generationProperties.getTemperature().getEnvironmentTemperature(),
                generationProperties.getTime(),
                maxAllowedTemperature.smoothed(),
                minAllowedTemperature.smoothed(),
                new IntegerPointSequence(sample.getInterpolationPoints()),
                sample.getRandomPoints().getEnrichWithRandomPoints(),
                sample.getRandomPoints().getNewPointChance())
                .generate();

        return new ThermocoupleMeanTemperature(function.getValue());
    }

    public List<ThermocoupleTemperature> thermocouplesTemperatures(MinAllowedTemperature minAllowedTemperature,
                                                                   MaxAllowedTemperature maxAllowedTemperature,
                                                                   ThermocoupleMeanTemperature thermocoupleMeanTemperature,
                                                                   Integer sampleNumber) {

        val sample = generationProperties.getSamples().get(sampleNumber);

        return new MeanChildFunctionsGenerator(
                generationProperties.getTime(),
                generationProperties.getTemperature().getEnvironmentTemperature(),
                thermocoupleMeanTemperature,
                maxAllowedTemperature.smoothed(),
                minAllowedTemperature.smoothed(),
                sample.getThermocoupleCount())
                .generate()
                .stream()
                .map(f -> new ThermocoupleTemperature(f.getValue()))
                .collect(Collectors.toList());
    }

    public SamplePressure excessPressure(){

        return new ExcessPressureGenerator(
                generationProperties.getTime(),
                generationProperties.getPressure().getDelta()
        ).generate();
    }
}
