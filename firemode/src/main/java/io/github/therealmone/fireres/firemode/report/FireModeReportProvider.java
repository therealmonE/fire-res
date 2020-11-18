package io.github.therealmone.fireres.firemode.report;

import io.github.therealmone.fireres.core.MeanFunctionFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.DecreasingChildFunctionGeneratorStrategy;
import io.github.therealmone.fireres.firemode.factory.FireModeFactory;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FireModeReportProvider implements Provider<FireModeReport> {

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

        List<FireModeSample> samples = properties.getSamples().stream()
                .map(sample -> {
                    val strategy = new DecreasingChildFunctionGeneratorStrategy(
                            properties.getGeneral().getEnvironmentTemperature(),
                            minAllowedTemp, maxAllowedTemp);

                    val meanFactory = new MeanFunctionFactory(properties, minAllowedTemp, maxAllowedTemp);

                    val meanWithChildFunctions = meanFactory
                            .meanWithChildFunctions(sample.getFireMode(), strategy, sample.getFireMode().getThermocoupleCount());

                    return FireModeSample.builder()
                            .thermocoupleMeanTemperature(new ThermocoupleMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                            .thermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                                    .map(child -> new ThermocoupleTemperature(child.getValue()))
                                    .collect(Collectors.toList()))
                            .build();
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

}
