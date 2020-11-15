package io.github.therealmone.fireres.core.unheated;

import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.config.SampleProperties;
import io.github.therealmone.fireres.core.common.generator.MeanChildFunctionsGenerator;
import io.github.therealmone.fireres.core.common.generator.MeanFunctionGenerator;
import io.github.therealmone.fireres.core.common.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.core.unheated.generator.UnheatedSurfaceMeanBoundGenerator;
import io.github.therealmone.fireres.core.unheated.generator.UnheatedSurfaceThermocoupleBoundGenerator;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceMeanBound;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;
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

        val sample = generationProperties.getSamples().get(sampleNumber);
        val group = sample.getUnheatedSurface().getFirstGroup();

        val meanTemperature = meanTemperature(group, meanBound);
        val thermocoupleTemperatures = thermocoupleTemperatures(
                group.getThermocoupleCount(), meanTemperature, thermocoupleBound);

        return UnheatedSurfaceGroup.builder()
                .meanBound(meanBound)
                .meanTemperature(meanTemperature)
                .thermocoupleBound(thermocoupleBound)
                .thermocoupleTemperatures(thermocoupleTemperatures)
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
        val thermocoupleBound = constantFunction(
                generationProperties.getGeneral().getTime(),
                group.getBound());

        //using same bound as thermocouple for 3rd group
        val meanTemperature = meanTemperature(group, thermocoupleBound);
        val thermocoupleTemperatures = thermocoupleTemperatures(
                group.getThermocoupleCount(), meanTemperature, thermocoupleBound);

        return UnheatedSurfaceGroup.builder()
                .meanBound(null) //no mean bound for 3rd group
                .meanTemperature(meanTemperature)
                .thermocoupleBound(new UnheatedSurfaceThermocoupleBound(thermocoupleBound.getValue()))
                .thermocoupleTemperatures(thermocoupleTemperatures)
                .build();
    }

    private List<UnheatedSurfaceThermocoupleTemperature> thermocoupleTemperatures(Integer thermocoupleCount,
                                                                                  IntegerPointSequence meanTemperature,
                                                                                  IntegerPointSequence thermocoupleBound) {
        val time = generationProperties.getGeneral().getTime();

        val thermocoupleTemperatures = new MeanChildFunctionsGenerator(
                time,
                generationProperties.getGeneral().getEnvironmentTemperature(),
                meanTemperature,
                thermocoupleBound,
                constantFunction(time, 0),
                thermocoupleCount
        ).generate();

        return thermocoupleTemperatures.stream()
                .map(t -> new UnheatedSurfaceThermocoupleTemperature(t.getValue()))
                .collect(Collectors.toList());
    }

    private UnheatedSurfaceMeanTemperature meanTemperature(UnheatedSurfaceGroupProperties group,
                                                           IntegerPointSequence meanBound) {

        val time = generationProperties.getGeneral().getTime();

        val meanFunction = new MeanFunctionGenerator(
                generationProperties.getGeneral().getEnvironmentTemperature(),
                time,
                meanBound,
                constantFunction(time, 0),
                new IntegerPointSequence(group.getInterpolationPoints()),
                group.getRandomPoints().getEnrichWithRandomPoints(),
                group.getRandomPoints().getNewPointChance()
        ).generate();

        return new UnheatedSurfaceMeanTemperature(meanFunction.getValue());
    }

}
