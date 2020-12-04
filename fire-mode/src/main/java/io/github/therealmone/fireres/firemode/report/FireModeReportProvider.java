package io.github.therealmone.fireres.firemode.report;

import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.firemode.factory.FireModeFactory;
import io.github.therealmone.fireres.firemode.generator.FireModeGeneratorStrategy;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
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

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Inject
    private FireModeFactory factory;

    @Override
    public FireModeReport get() {
        log.info("Building fire mode report");

        val standardTemp = factory.standardTemperature();
        val maxAllowedTemp = factory.maxAllowedTemperature(standardTemp);
        val minAllowedTemp = factory.minAllowedTemperature(standardTemp);
        val furnaceTemp = factory.furnaceTemperature(standardTemp);

        List<FireModeSample> samples = properties.getSamples().stream()
                .map(sample -> mapToFireModeSample(maxAllowedTemp, minAllowedTemp, sample))
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

    private FireModeSample mapToFireModeSample(MaxAllowedTemperature maxAllowedTemp, MinAllowedTemperature minAllowedTemp, SampleProperties sample) {
        val lowerBound = new IntegerPointSequence(minAllowedTemp.getSmoothedValue());
        val upperBound = new IntegerPointSequence(maxAllowedTemp.getSmoothedValue());

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(sample.getFireMode())
                        .meanLowerBound(lowerBound)
                        .meanUpperBound(upperBound)
                        .childLowerBound(lowerBound)
                        .childUpperBound(upperBound)
                        .childFunctionsCount(sample.getFireMode().getThermocoupleCount())
                        .strategy(new FireModeGeneratorStrategy())
                        .build());

        return FireModeSample.builder()
                .thermocoupleMeanTemperature(new ThermocoupleMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                .thermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                        .map(child -> new ThermocoupleTemperature(child.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }

}
