package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.FireModeGeneratorStrategy;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeEnrichType.SAMPLES_TEMPERATURE;

public class SamplesTemperatureSEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(FireModeReport report) {
        val lowerBound = new IntegerPointSequence(report.getMinAllowedTemperature().getSmoothedValue());
        val upperBound = new IntegerPointSequence(report.getMaxAllowedTemperature().getSmoothedValue());

        report.setSamples(new ArrayList<>());

        generationProperties.getSamples().forEach(sample -> {
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

            val fireModeSample = FireModeSample.builder()
                    .thermocoupleMeanTemperature(new ThermocoupleMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                    .thermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                            .map(child -> new ThermocoupleTemperature(child.getValue()))
                            .collect(Collectors.toList()))
                    .build();

            report.getSamples().add(fireModeSample);
        });
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLES_TEMPERATURE.equals(enrichType);
    }
}
