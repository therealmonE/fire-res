package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.exception.InvalidMeanFunctionException;
import io.github.therealmone.fireres.core.factory.FireModeFactory;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Sample;
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

        val factory = new FireModeFactory(properties);

        val standardTemp = factory.standardTemperature();
        val maxAllowedTemp = factory.maxAllowedTemperature(standardTemp);
        val minAllowedTemp = factory.minAllowedTemperature(standardTemp);
        val furnaceTemp = factory.furnaceTemperature(standardTemp);

        List<Sample> samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(i -> {
                    for (int j = 0; j < ATTEMPTS; j++) {
                        try {
                            return tryToGenerateSample(factory, maxAllowedTemp, minAllowedTemp, i);
                        } catch (InvalidMeanFunctionException e) {
                            log.error("Invalid mean temperature, trying to generate new one...");
                        }
                    }

                    throw new ImpossibleGenerationException();
                })
                .collect(Collectors.toList());

        log.info("Report was built successfully");
        return Report.builder()
                .time(properties.getGeneral().getTime())
                .environmentTemperature(properties.getGeneral().getEnvironmentTemperature())
                .furnaceTemperature(furnaceTemp)
                .minAllowedTemperature(minAllowedTemp)
                .maxAllowedTemperature(maxAllowedTemp)
                .standardTemperature(standardTemp)
                .samples(samples)
                .build();
    }

    private static Sample tryToGenerateSample(FireModeFactory factory,
                                              MaxAllowedTemperature maxAllowedTemp,
                                              MinAllowedTemperature minAllowedTemp,
                                              Integer sampleNumber) {

        val meanTemp = factory.thermocoupleMeanTemperature(
                sampleNumber, minAllowedTemp, maxAllowedTemp);

        return Sample.builder()
                .thermocoupleMeanTemperature(meanTemp)
                .thermocoupleTemperatures(factory.thermocouplesTemperatures(
                        minAllowedTemp, maxAllowedTemp, meanTemp, sampleNumber))
                .build();
    }

}
