package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.FunctionsGenerationBounds;
import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.heatflow.generator.HeatFlowGenerationStrategy;
import io.github.therealmone.fireres.heatflow.model.MeanTemperature;
import io.github.therealmone.fireres.heatflow.model.SensorTemperature;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.NormalizationService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;

@Slf4j
public class MeanWithSensorsTemperaturesEnricher implements ReportEnricher<HeatFlowReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FunctionsGenerationService functionsGenerationService;

    @Inject
    private NormalizationService normalizationService;

    @Override
    public void enrich(HeatFlowReport report) {
        val time = generationProperties.getGeneral().getTime();
        val bound = normalizationService.disnormalize(report.getBound()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedFlowShift()));
        val zeroBound = constantFunction(time, 0);

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(report.getProperties().getFunctionForm())
                        .meanBounds(new FunctionsGenerationBounds(zeroBound, bound))
                        .childrenBounds(new FunctionsGenerationBounds(zeroBound, bound))
                        .childFunctionsCount(report.getProperties().getSensorCount())
                        .strategy(new HeatFlowGenerationStrategy())
                        .build());

        report.setMeanTemperature(new MeanTemperature(
                normalizationService.normalize(meanWithChildFunctions.getFirst()).getValue()));

        report.setSensorTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new SensorTemperature(
                        normalizationService.normalize(child).getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MEAN_WITH_SENSORS_TEMPERATURES.equals(enrichType);
    }
}
