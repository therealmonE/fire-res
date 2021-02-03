package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.heatflow.generator.HeatFlowGeneratorStrategy;
import io.github.therealmone.fireres.heatflow.model.HeatFlowMeanTemperature;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSensorTemperature;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
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
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(HeatFlowReport report) {
        val time = generationProperties.getGeneral().getTime();
        val bound = report.getBound();
        val zeroBound = constantFunction(time, 0);

        val sampleProperties = report.getSample().getSampleProperties();

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(sampleProperties.getHeatFlow())
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(bound)
                        .childFunctionsCount(sampleProperties.getHeatFlow().getSensorCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(bound)
                        .strategy(new HeatFlowGeneratorStrategy())
                        .build());

        report.setMeanTemperature(new HeatFlowMeanTemperature(meanWithChildFunctions.getFirst().getValue()));

        report.setSensorTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new HeatFlowSensorTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MEAN_WITH_SENSORS_TEMPERATURES.equals(enrichType);
    }
}
