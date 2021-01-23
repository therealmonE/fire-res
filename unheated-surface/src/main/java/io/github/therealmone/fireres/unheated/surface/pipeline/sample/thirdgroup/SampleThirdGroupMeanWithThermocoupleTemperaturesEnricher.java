package io.github.therealmone.fireres.unheated.surface.pipeline.sample.thirdgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceGeneratorStrategy;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleTemperature;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

@Slf4j
public class SampleThirdGroupMeanWithThermocoupleTemperaturesEnricher implements
        SampleEnricher<UnheatedSurfaceReport, UnheatedSurfaceSample> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(UnheatedSurfaceReport report, UnheatedSurfaceSample sample) {
        log.info("Unheated surface: enriching sample {} second group with mean and thermocouple temperatures", sample.getId());
        val time = generationProperties.getGeneral().getTime();
        val thermocoupleBound = sample.getThirdGroup().getThermocoupleBound();
        val zeroBound = constantFunction(time, 0);

        val sampleProperties = generationProperties.getSampleById(sample.getId());
        val groupProperties = sampleProperties.getUnheatedSurface().getThirdGroup();

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(groupProperties)
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(thermocoupleBound)
                        .childFunctionsCount(groupProperties.getThermocoupleCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(thermocoupleBound)
                        .strategy(new UnheatedSurfaceGeneratorStrategy())
                        .build());

        sample.getThirdGroup().setMeanTemperature(new UnheatedSurfaceMeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        sample.getThirdGroup().setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new UnheatedSurfaceThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return SAMPLE_THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES.equals(enrichType);
    }

}
