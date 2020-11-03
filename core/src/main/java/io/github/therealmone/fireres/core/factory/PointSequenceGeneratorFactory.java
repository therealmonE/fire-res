package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.config.Coefficients;
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
import lombok.val;


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
                generationProperties.getTime(),
                generationProperties.getTemperature().getEnvironmentTemperature(),
                standardTemp);
    }

    public PointSequenceGenerator<MinAllowedTemperature> minAllowedTempGenerator(StandardTemperature standardTemp) {
        return new MinAllowedTempGenerator(
                generationProperties.getTime(),
                new Coefficients(generationProperties.getTemperature().getMinAllowedTempCoefficients()),
                standardTemp);
    }

    public PointSequenceGenerator<MaxAllowedTemperature> maxAllowedTempGenerator(StandardTemperature standardTemp) {
        return new MaxAllowedTempGenerator(
                generationProperties.getTime(),
                new Coefficients(generationProperties.getTemperature().getMaxAllowedTempCoefficients()),
                standardTemp);
    }

    public PointSequenceGenerator<ThermocoupleMeanTemperature> thermocoupleMeanGenerator(Integer sampleNumber,
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
                sample.getRandomPoints().getNewPointChance());
    }

    public MultiplePointSequencesGenerator<ThermocoupleTemperature> thermocouplesTempGenerator(MinAllowedTemperature minAllowedTemperature,
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
                sample.getThermocoupleCount());
    }

}
