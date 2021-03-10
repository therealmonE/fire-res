package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.FunctionsGenerationBounds;
import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.firemode.generator.FireModeGenerationStrategy;
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
    private FunctionsGenerationService functionsGenerationService;

    @Override
    public void enrich(FireModeReport report) {
        val lowerBound = new IntegerPointSequence(
                report.getMinAllowedTemperature()
                        .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedTemperatureShift()));

        val upperBound = new IntegerPointSequence(
                report.getMaxAllowedTemperature()
                        .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift()));

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanBounds(new FunctionsGenerationBounds(lowerBound, upperBound))
                        .childrenBounds(new FunctionsGenerationBounds(lowerBound, upperBound))
                        .childFunctionsCount(report.getProperties().getThermocoupleCount())
                        .strategy(new FireModeGenerationStrategy())
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
