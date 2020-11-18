package io.github.therealmone.fireres.core;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.Interpolated;
import io.github.therealmone.fireres.core.generator.ChildFunctionGeneratorStrategy;
import io.github.therealmone.fireres.core.generator.MeanChildFunctionsGenerator;
import io.github.therealmone.fireres.core.generator.MeanFunctionGenerator;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.exception.InvalidMeanFunctionException;
import io.github.therealmone.fireres.core.exception.MeanChildFunctionGenerationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MeanFunctionFactory {

    private static final Integer MEAN_FUNCTION_GENERATION_ATTEMPTS = 100;
    private static final Integer CHILD_FUNCTIONS_GENERATION_ATTEMPTS = 100;

    private final GenerationProperties generationProperties;
    private final IntegerPointSequence meanLowerBound;
    private final IntegerPointSequence meanUpperBound;

    public Pair<IntegerPointSequence, List<IntegerPointSequence>> meanWithChildFunctions(Interpolated interpolation,
                                                                                         ChildFunctionGeneratorStrategy strategy,
                                                                                         Integer functionsCount) {
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();
        val time = generationProperties.getGeneral().getTime();

        for (int i = 0; i < MEAN_FUNCTION_GENERATION_ATTEMPTS; i++) {
            try {
                val mean = meanFunction(t0, time, meanLowerBound, meanUpperBound, interpolation);
                val child = childFunctions(time, mean, functionsCount, strategy);

                return new Pair<>(mean, child);
            } catch (InvalidMeanFunctionException e) {
                log.error("Invalid mean function, generating new one");
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence meanFunction(Integer t0, Integer time, IntegerPointSequence lowerBound,
                                              IntegerPointSequence upperBound, Interpolated interpolation) {
        return new MeanFunctionGenerator(
                t0, time, upperBound, lowerBound,
                new IntegerPointSequence(interpolation.getInterpolationPoints()),
                interpolation.getRandomPoints().getEnrichWithRandomPoints(),
                interpolation.getRandomPoints().getNewPointChance()
        ).generate();
    }

    private List<IntegerPointSequence> childFunctions(Integer time, IntegerPointSequence meanFunction,
                                                      Integer functionsCount, ChildFunctionGeneratorStrategy strategy) {

        for (int i = 0; i < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; i++) {
            try {
                return new MeanChildFunctionsGenerator(
                        time,
                        meanFunction,
                        functionsCount,
                        strategy
                ).generate();
            } catch (MeanChildFunctionGenerationException e) {
                log.error("Failed to generate mean child functions");
            }
        }

        throw new InvalidMeanFunctionException();
    }
}
