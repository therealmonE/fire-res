package io.github.therealmone.fireres.heatflow.factory;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.heatflow.model.*;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public class HeatFlowFactory {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    private HeatFlowBound bound(Integer sampleNumber) {
        val sample = generationProperties.getSamples().get(sampleNumber);
        return new HeatFlowBound(
                constantFunction(
                    generationProperties.getGeneral().getTime(),
                    sample.getHeatFlow().getBound())
                .getValue());
    }

   public HeatFlowSample heatFlowSample(Integer sampleNumber) {
        val bound = bound(sampleNumber);
        val zeroBound = constantFunction(generationProperties.getGeneral().getTime(), 0);

        val sample = generationProperties.getSamples().get(sampleNumber);

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(sample.getHeatFlow())
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(bound)
                        .childFunctionsCount(sample.getHeatFlow().getSensorCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(bound)
                        .strategy(new HeatFlowChildFunctionGeneratorStrategy())
                        .build());

        return HeatFlowSample.builder()
                .bound(bound)
                .meanTemperature(new HeatFlowMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                .sensorTemperatures(meanWithChildFunctions.getSecond().stream()
                        .map(child -> new HeatFlowSensorTemperature(child.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }
}