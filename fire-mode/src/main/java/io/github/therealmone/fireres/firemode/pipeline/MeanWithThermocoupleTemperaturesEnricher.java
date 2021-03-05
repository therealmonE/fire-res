package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.firemode.generator.FireModeGeneratorStrategy;
import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class MeanWithThermocoupleTemperaturesEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(FireModeReport report) {
        val lowerBound = new IntegerPointSequence(report.getMinAllowedTemperature().getSmoothedValue());
        val upperBound = new IntegerPointSequence(report.getMaxAllowedTemperature().getSmoothedValue());

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanLowerBound(lowerBound)
                        .meanUpperBound(upperBound)
                        .childLowerBound(lowerBound)
                        .childUpperBound(upperBound)
                        .childFunctionsCount(report.getProperties().getThermocoupleCount())
                        .strategy(new FireModeGeneratorStrategy())
                        .build());

        report.setThermocoupleMeanTemperature(new ThermocoupleMeanTemperature(meanWithChildFunctions.getFirst().getValue()));

        report.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new ThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }
}
