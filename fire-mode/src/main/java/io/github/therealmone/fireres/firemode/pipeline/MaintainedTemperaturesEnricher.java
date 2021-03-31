package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.FunctionsGenerationBounds;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.MaintainedFunctionsGenerationParams;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.firemode.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.firemode.generator.MaxAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.generator.MinAllowedTempGenerator;
import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.firemode.model.MaintainedTemperatures;
import io.github.therealmone.fireres.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.calculateMeanFunction;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.core.utils.TemperatureMaintainingUtils.shouldMaintainTemperature;
import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MAINTAINED_TEMPERATURES;

public class MaintainedTemperaturesEnricher implements ReportEnricher<FireModeReport> {

    @Inject
    private FunctionsGenerationService functionsGenerationService;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(FireModeReport report) {
        report.setMaintainedTemperatures(null);

        val temperatureMaintaining = report.getProperties().getTemperaturesMaintaining();

        if (temperatureMaintaining != null) {
            resolveTemperatureMaintainingTime(report).ifPresent(integer ->
                    report.setMaintainedTemperatures(generateMaintainedTemperatures(report, integer)));
        }
    }

    private MaintainedTemperatures generateMaintainedTemperatures(FireModeReport report, Integer temperatureMaintainingTime) {
        val standardTemperature = generateMaintainedStandardTemperature(report.getProperties().getTemperaturesMaintaining(), temperatureMaintainingTime);
        val furnaceTemperature = generateMaintainedFurnaceTemperature(standardTemperature);
        val minAllowedTemperature = generateMaintainedMinAllowedTemperature(standardTemperature);
        val maxAllowedTemperature = generateMaintainedMaxAllowedTemperature(standardTemperature);

        val thermocoupleTemperatures = generateMaintainedThermocoupleTemperatures(
                minAllowedTemperature, maxAllowedTemperature, report, temperatureMaintainingTime);

        val meanTemperature = new ThermocoupleMeanTemperature(calculateMeanFunction(thermocoupleTemperatures).getValue());

        return MaintainedTemperatures.builder()
                .standardTemperature(standardTemperature)
                .furnaceTemperature(furnaceTemperature)
                .minAllowedTemperature(minAllowedTemperature)
                .maxAllowedTemperature(maxAllowedTemperature)
                .thermocoupleTemperatures(thermocoupleTemperatures)
                .thermocoupleMeanTemperature(meanTemperature)
                .build();
    }

    private FurnaceTemperature generateMaintainedFurnaceTemperature(StandardTemperature standardTemperature) {
        return new FurnaceTempGenerator(generationProperties.getGeneral().getEnvironmentTemperature(), standardTemperature).generate();
    }

    private List<ThermocoupleTemperature> generateMaintainedThermocoupleTemperatures(MinAllowedTemperature minAllowedTemperature,
                                                                                     MaxAllowedTemperature maxAllowedTemperature,
                                                                                     FireModeReport report,
                                                                                     Integer temperatureMaintainingTime) {

        val lowerBound = new IntegerPointSequence(minAllowedTemperature.getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedTemperatureShift()));
        val upperBound = new IntegerPointSequence(maxAllowedTemperature.getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift()));

        val thermocoupleTemperatures = functionsGenerationService.maintainedFunctions(MaintainedFunctionsGenerationParams.builder()
                .tStart(temperatureMaintainingTime)
                .tEnd(generationProperties.getGeneral().getTime())
                .bounds(FunctionsGenerationBounds.builder()
                        .lowerBound(lowerBound)
                        .upperBound(upperBound)
                        .build())
                .functionsCount(report.getProperties().getThermocoupleCount())
                .temperature(report.getProperties().getTemperaturesMaintaining())
                .build());

        return thermocoupleTemperatures.stream()
                .map(t -> new ThermocoupleTemperature(t.getValue()))
                .collect(Collectors.toList());
    }

    private MaxAllowedTemperature generateMaintainedMaxAllowedTemperature(StandardTemperature standardTemperature) {
        return new MaxAllowedTempGenerator(standardTemperature).generate();
    }

    private MinAllowedTemperature generateMaintainedMinAllowedTemperature(StandardTemperature standardTemperature) {
        return new MinAllowedTempGenerator(standardTemperature).generate();
    }

    private StandardTemperature generateMaintainedStandardTemperature(Integer temperature, Integer temperatureMaintainingTime) {
        return new StandardTemperature(constantFunction(
                temperatureMaintainingTime,
                generationProperties.getGeneral().getTime(),
                temperature).getValue());
    }

    private Optional<Integer> resolveTemperatureMaintainingTime(FireModeReport report) {
        val standardTemperature = report.getStandardTemperature();
        val temperatureMaintaining = report.getProperties().getTemperaturesMaintaining();

        for (IntegerPoint point : standardTemperature.getValue()) {
            if (shouldMaintainTemperature(point.getValue(), temperatureMaintaining)) {
                return Optional.of(point.getTime());
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAINTAINED_TEMPERATURES.equals(enrichType);
    }

}
