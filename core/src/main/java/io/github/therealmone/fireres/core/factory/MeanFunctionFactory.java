package io.github.therealmone.fireres.core.factory;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
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
    private static final Integer CHILD_FUNCTIONS_GENERATION_ATTEMPTS = 10;

    @Inject
    private GenerationProperties generationProperties;

    public Pair<IntegerPointSequence, List<IntegerPointSequence>> meanWithChildFunctions(MeanWithChildFunctionGenerationParameters generationParameters) {
        for (int i = 0; i < MEAN_FUNCTION_GENERATION_ATTEMPTS; i++) {
            try {
                val mean = meanFunction(generationParameters);
                val child = childFunctions(generationParameters, mean);

                return new Pair<>(mean, child);
            } catch (InvalidMeanFunctionException e) {
                log.error("Invalid mean function, generating new one");
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence meanFunction(MeanWithChildFunctionGenerationParameters meanWithChildFunctionsParameters) {
        val time = generationProperties.getGeneral().getTime();
        val environmentTemperature = generationProperties.getGeneral().getEnvironmentTemperature();
        val lowerBound = meanWithChildFunctionsParameters.getMeanLowerBound();
        val upperBound = meanWithChildFunctionsParameters.getMeanUpperBound();
        val interpolation = meanWithChildFunctionsParameters.getMeanFunctionForm();

        return new MeanFunctionGenerator(
                environmentTemperature,
                time,
                upperBound,
                lowerBound,
                interpolation
        ).generate();
    }

    public List<IntegerPointSequence> childFunctions(MeanWithChildFunctionGenerationParameters meanWithChildFunctionsParameters,
                                                     IntegerPointSequence meanFunction) {

        val time = generationProperties.getGeneral().getTime();
        val environmentTemperature = generationProperties.getGeneral().getEnvironmentTemperature();
        val lowerBound = meanWithChildFunctionsParameters.getChildLowerBound();
        val upperBound = meanWithChildFunctionsParameters.getChildUpperBound();
        val functionsCount = meanWithChildFunctionsParameters.getChildFunctionsCount();

        for (int i = 0; i < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; i++) {
            try {
                return new MeanChildFunctionsGenerator(
                        time,
                        environmentTemperature,
                        functionsCount,
                        meanFunction,
                        lowerBound,
                        upperBound,
                        meanWithChildFunctionsParameters.getStrategy(),
                        meanWithChildFunctionsParameters.getMeanFunctionForm().getNonLinearityCoefficient()
                ).generate();
            } catch (MeanChildFunctionGenerationException e) {
                log.error("Failed to generate mean child functions");
            }
        }

        throw new InvalidMeanFunctionException();
    }
}
