package io.github.therealmone.fireres.heatflow.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.heatflow.generator.HeatFlowGeneratorStrategy;
import io.github.therealmone.fireres.heatflow.model.HeatFlowMeanTemperature;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSensorTemperature;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.pipeline.sample.HeatFlowSampleEnrichType.SAMPLE_MEAN_WITH_SENSORS_TEMPERATURES;

@Slf4j
public class SampleMeanWithSensorsTemperaturesEnricher implements SampleEnricher<HeatFlowReport, HeatFlowSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(HeatFlowReport report, HeatFlowSample sample) {
        log.info("Heat flow: enriching sample {} with mean and sensors temperatures", sample.getId());
        val time = generationProperties.getGeneral().getTime();
        val bound = sample.getBound();
        val zeroBound = constantFunction(time, 0);

        val sampleProperties = generationProperties.getSampleById(sample.getId());

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

        sample.setMeanTemperature(new HeatFlowMeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        sample.setSensorTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new HeatFlowSensorTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_MEAN_WITH_SENSORS_TEMPERATURES.equals(enrichType);
    }
}
