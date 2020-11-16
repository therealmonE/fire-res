package io.github.therealmone.fireres.core.firemode.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.exception.InvalidMeanFunctionException;
import io.github.therealmone.fireres.core.firemode.FireModeFactory;
import io.github.therealmone.fireres.core.firemode.model.FireModeSample;
import io.github.therealmone.fireres.core.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.firemode.model.MinAllowedTemperature;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class FireModeReportProvider implements Provider<FireModeReport> {

    private static final Integer ATTEMPTS = 100;

    @Inject
    private GenerationProperties properties;

    @Override
    public FireModeReport get() {
        log.info("Building fire mode report");
        val factory = new FireModeFactory(properties);

        val standardTemp = factory.standardTemperature();
        val maxAllowedTemp = factory.maxAllowedTemperature(standardTemp);
        val minAllowedTemp = factory.minAllowedTemperature(standardTemp);
        val furnaceTemp = factory.furnaceTemperature(standardTemp);

        val samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(i -> {
                    for (int j = 0; j < ATTEMPTS; j++) {
                        try {
                            return tryToGenerateSample(factory, maxAllowedTemp, minAllowedTemp, i);
                        } catch (InvalidMeanFunctionException e) {
                            log.error("Invalid fire mode mean temperature, trying to generate new one...");
                        }
                    }

                    throw new ImpossibleGenerationException();
                })
                .collect(Collectors.toList());

        log.info("Fire mode report was built successfully");
        return FireModeReport.builder()
                .furnaceTemperature(furnaceTemp)
                .minAllowedTemperature(minAllowedTemp)
                .maxAllowedTemperature(maxAllowedTemp)
                .standardTemperature(standardTemp)
                .samples(samples)
                .build();
    }

    private static FireModeSample tryToGenerateSample(FireModeFactory factory,
                                                      MaxAllowedTemperature maxAllowedTemp,
                                                      MinAllowedTemperature minAllowedTemp,
                                                      Integer sampleNumber) {

        val meanTemp = factory.thermocoupleMeanTemperature(
                sampleNumber, minAllowedTemp, maxAllowedTemp);

        return FireModeSample.builder()
                .thermocoupleMeanTemperature(meanTemp)
                .thermocoupleTemperatures(factory.thermocouplesTemperatures(
                        minAllowedTemp, maxAllowedTemp, meanTemp, sampleNumber))
                .build();
    }

}
