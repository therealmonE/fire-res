package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.exception.InvalidMeanTemperatureException;
import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ReportBuilder {

    private static final Integer ATTEMPTS = 100;

    public static Report build(GenerationProperties properties) {
        log.info("Building report");
        Validator.validateGenerationProperties(properties);

        val factory = new PointSequenceGeneratorFactory(properties);

        val standardTemp = factory.standardTempGenerator().generate();
        val maxAllowedTemp = factory.maxAllowedTempGenerator(standardTemp).generate();
        val minAllowedTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val furnaceTemp = factory.furnaceTempGenerator(standardTemp).generate();

        List<Sample> samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(i -> {
                    for (int j = 0; j < ATTEMPTS; j++) {
                        try {
                            return tryToGenerateSample(factory, maxAllowedTemp, minAllowedTemp, i);
                        } catch (InvalidMeanTemperatureException e) {
                            log.error("Invalid mean temperature, trying to generate new one...");
                        }
                    }

                    throw new ImpossibleGenerationException();
                })
                .collect(Collectors.toList());

        log.info("Report was built successfully");
        return Report.builder()
                .time(properties.getTime())
                .environmentTemperature(properties.getTemperature().getEnvironmentTemperature())
                .furnaceTemperature(furnaceTemp)
                .minAllowedTemperature(minAllowedTemp)
                .maxAllowedTemperature(maxAllowedTemp)
                .standardTemperature(standardTemp)
                .samples(samples)
                .build();
    }

    private static Sample tryToGenerateSample(PointSequenceGeneratorFactory factory,
                                              MaxAllowedTemperature maxAllowedTemp,
                                              MinAllowedTemperature minAllowedTemp,
                                              Integer sampleNumber) {

        val meanTemp = factory
                .thermocoupleMeanGenerator(sampleNumber, minAllowedTemp, maxAllowedTemp)
                .generate();

        return Sample.builder()
                .thermocoupleMeanTemperature(meanTemp)
                .thermocoupleTemperatures(factory.
                        thermocouplesTempGenerator(minAllowedTemp, maxAllowedTemp, meanTemp, sampleNumber)
                        .generate())
                .build();
    }

}
