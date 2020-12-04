package io.github.therealmone.fireres.unheated.surface.factory;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceGeneratorStrategy;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceMeanBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceThermocoupleBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanBound;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleTemperature;
import lombok.val;

import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public class UnheatedSurfaceFactory {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    public UnheatedSurfaceMeanBound meanBound() {
        return new UnheatedSurfaceMeanBoundGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getEnvironmentTemperature()
        ).generate();
    }

    public UnheatedSurfaceThermocoupleBound thermocoupleBound() {
        return new UnheatedSurfaceThermocoupleBoundGenerator(
                generationProperties.getGeneral().getTime(),
                generationProperties.getGeneral().getEnvironmentTemperature()
        ).generate();
    }

    public UnheatedSurfaceGroup firstThermocoupleGroup(Integer sampleNumber) {
        val meanBound = meanBound();
        val thermocoupleBound = thermocoupleBound();
        val zeroBound = constantFunction(generationProperties.getGeneral().getTime(), 0);

        val sample = generationProperties.getSamples().get(sampleNumber);
        val group = sample.getUnheatedSurface().getFirstGroup();

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(group)
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(meanBound)
                        .childFunctionsCount(group.getThermocoupleCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(thermocoupleBound)
                        .strategy(new UnheatedSurfaceGeneratorStrategy())
                        .build());

        return UnheatedSurfaceGroup.builder()
                .meanBound(meanBound)
                .meanTemperature(new UnheatedSurfaceMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                .thermocoupleBound(thermocoupleBound)
                .thermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                        .map(child -> new UnheatedSurfaceThermocoupleTemperature(child.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }


    public UnheatedSurfaceGroup secondThermocoupleGroup(Integer sampleNumber) {
        return secondaryGroup(sampleNumber, sample -> sample.getUnheatedSurface().getSecondGroup());
    }

    public UnheatedSurfaceGroup thirdThermocoupleGroup(Integer sampleNumber) {
        return secondaryGroup(sampleNumber, sample -> sample.getUnheatedSurface().getThirdGroup());
    }

    private UnheatedSurfaceGroup secondaryGroup(Integer sampleNumber,
                                                Function<SampleProperties, UnheatedSurfaceSecondaryGroupProperties> mapper) {

        val sample = generationProperties.getSamples().get(sampleNumber);
        val group = mapper.apply(sample);

        val zeroBound = constantFunction(generationProperties.getGeneral().getTime(), 0);
        val thermocoupleBound = new UnheatedSurfaceThermocoupleBound(constantFunction(
                generationProperties.getGeneral().getTime(),
                group.getBound()
        ).getValue());

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionInterpolation(group)
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(thermocoupleBound)
                        .childFunctionsCount(group.getThermocoupleCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(thermocoupleBound)
                        .strategy(new UnheatedSurfaceGeneratorStrategy())
                        .build());

        return UnheatedSurfaceGroup.builder()
                .meanBound(null) //no mean bound for 3rd group
                .meanTemperature(new UnheatedSurfaceMeanTemperature(meanWithChildFunctions.getFirst().getValue()))
                .thermocoupleBound(thermocoupleBound)
                .thermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                        .map(child -> new UnheatedSurfaceThermocoupleTemperature(child.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }

}
