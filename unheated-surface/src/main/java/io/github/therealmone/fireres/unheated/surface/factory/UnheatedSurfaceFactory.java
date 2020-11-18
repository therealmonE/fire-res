package io.github.therealmone.fireres.unheated.surface.factory;

import io.github.therealmone.fireres.core.MeanFunctionFactory;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.generator.IncreasingChildFunctionGeneratorStrategy;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceMeanBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceThermocoupleBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanBound;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

@RequiredArgsConstructor
public class UnheatedSurfaceFactory {

    private final GenerationProperties generationProperties;

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

        val meanFunctionFactory = new MeanFunctionFactory(generationProperties, zeroBound, meanBound);

        val strategy = new IncreasingChildFunctionGeneratorStrategy(
                generationProperties.getGeneral().getEnvironmentTemperature(),
                zeroBound,
                thermocoupleBound);

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(group, strategy, group.getThermocoupleCount());

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

        val meanFunctionFactory = new MeanFunctionFactory(generationProperties, zeroBound, thermocoupleBound);

        val strategy = new IncreasingChildFunctionGeneratorStrategy(
                generationProperties.getGeneral().getEnvironmentTemperature(),
                zeroBound,
                thermocoupleBound());

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(group, strategy, group.getThermocoupleCount());

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
