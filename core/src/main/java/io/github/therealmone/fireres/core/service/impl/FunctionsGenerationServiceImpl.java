package io.github.therealmone.fireres.core.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.exception.ImpossibleGenerationException;
import io.github.therealmone.fireres.core.exception.FunctionGenerationException;
import io.github.therealmone.fireres.core.generator.ChildrenInterpolationPointsGenerator;
import io.github.therealmone.fireres.core.generator.FunctionGenerator;
import io.github.therealmone.fireres.core.generator.SimilarFunctionGenerator;
import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.core.service.ValidationService;
import io.github.therealmone.fireres.core.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class FunctionsGenerationServiceImpl implements FunctionsGenerationService {

    private static final Integer BASIS_FUNCTION_GENERATION_ATTEMPTS = 100;
    private static final Integer CHILD_FUNCTIONS_GENERATION_ATTEMPTS = 100;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ValidationService validationService;

    @Override
    public Pair<IntegerPointSequence, List<IntegerPointSequence>> meanWithChildFunctions(
            FunctionsGenerationParams generationParameters
    ) {
        if (!validationService.isFunctionGenerationParamsValid(generationParameters)) {
            throw new ImpossibleGenerationException();
        }

        val childFunctionsForms = mapToChildFunctionsForms(generationParameters);

        return generateMeanWithChildFunctions(generationParameters, childFunctionsForms);
    }

    private Pair<IntegerPointSequence, List<IntegerPointSequence>> generateMeanWithChildFunctions(FunctionsGenerationParams params,
                                                                                                  List<FunctionForm<Integer>> childFunctionsForms) {

        for (int basisAttempt = 0; basisAttempt < BASIS_FUNCTION_GENERATION_ATTEMPTS; basisAttempt++) {
            val basis = generateBasis(params);
            val childFunctions = new ArrayList<IntegerPointSequence>();

            try {
                for (int i = 0; i < params.getChildFunctionsCount(); i++) {
                    val childFunctionForm = childFunctionsForms.get(i);
                    val childFunction = generateChildFunction(basis, childFunctionForm, params);

                    childFunctions.add(childFunction);
                }

                return Pair.create(calculateMeanFunction(childFunctions), childFunctions);
            } catch (ImpossibleGenerationException e) {
                log.error("Can't generate child function, generating new basis, attempt: {}", basisAttempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence generateChildFunction(IntegerPointSequence basis,
                                                       FunctionForm<Integer> functionForm,
                                                       FunctionsGenerationParams params) {

        for (int childAttempt = 0; childAttempt < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; childAttempt++) {
            try {
                return SimilarFunctionGenerator.builder()
                        .basis(basis)
                        .originalForm(functionForm)
                        .lowerBound(params.getChildrenBounds().getLowerBound())
                        .upperBound(params.getChildrenBounds().getUpperBound())
                        .t0(generationProperties.getGeneral().getEnvironmentTemperature())
                        .time(generationProperties.getGeneral().getTime())
                        .functionsGenerationStrategy(params.getStrategy())
                        .build()
                        .generate();
            } catch (FunctionGenerationException e) {
                log.error("Failed to generate child function, attempt: {}", childAttempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence generateBasis(FunctionsGenerationParams generationParams) {
        for (int attempt = 0; attempt < CHILD_FUNCTIONS_GENERATION_ATTEMPTS; attempt++) {
            try {
                return FunctionGenerator.builder()
                        .functionForm(generationParams.getMeanFunctionForm())
                        .lowerBound(generationParams.getMeanBounds().getLowerBound())
                        .upperBound(generationParams.getMeanBounds().getUpperBound())
                        .t0(generationProperties.getGeneral().getEnvironmentTemperature())
                        .time(generationProperties.getGeneral().getTime())
                        .build()
                        .generate();
            } catch (FunctionGenerationException e) {
                log.error("Failed to generate basis, attempt: {}", attempt);
            }
        }

        throw new ImpossibleGenerationException();
    }

    private IntegerPointSequence calculateMeanFunction(List<IntegerPointSequence> childFunctions) {
        if (childFunctions == null || childFunctions.isEmpty()) {
            throw new IllegalArgumentException();
        }

        val meanFunction = new ArrayList<IntegerPoint>();

        for (int i = 0; i < childFunctions.get(0).getValue().size(); i++) {
            val pointIndex = i;
            val points = childFunctions.stream()
                    .map(childFunction -> childFunction.getPoint(pointIndex))
                    .collect(Collectors.toList());

            meanFunction.add(new IntegerPoint(i, MathUtils.calculatePointsMeanValue(points)));
        }

        return new IntegerPointSequence(meanFunction);
    }

    private List<FunctionForm<Integer>> mapToChildFunctionsForms(FunctionsGenerationParams params) {
        val childForms = initializeChildrenForms(params);

        initializeInterpolationPoints(childForms, params);

        return childForms;
    }

    private void initializeInterpolationPoints(List<FunctionForm<Integer>> childForms,
                                               FunctionsGenerationParams params) {

        val envTemp = generationProperties.getGeneral().getEnvironmentTemperature();

        for (Point<?> meanPoint : params.getMeanFunctionForm().getInterpolationPoints()) {
            val delta = params.getStrategy().resolveDelta(envTemp, meanPoint.getIntValue());

            val min = Math.max(
                    params.getChildrenBounds().getLowerBound().getPoint(meanPoint.getTime()).getValue(),
                    meanPoint.getIntValue() - delta);

            val max = Math.min(
                    params.getChildrenBounds().getUpperBound().getPoint(meanPoint.getTime()).getValue(),
                    meanPoint.getIntValue() + delta);

            val childrenPoints = ChildrenInterpolationPointsGenerator.builder()
                    .childForms(childForms)
                    .childrenCount(params.getChildFunctionsCount())
                    .time(meanPoint.getTime())
                    .meanValue(meanPoint.getIntValue())
                    .lowerBound(min)
                    .upperBound(max)
                    .build()
                    .generate();

            for (int i = 0; i < childrenPoints.getValue().size(); i++) {
                childForms.get(i).getInterpolationPoints().add(childrenPoints.getValue().get(i));
            }
        }
    }


    private List<FunctionForm<Integer>> initializeChildrenForms(FunctionsGenerationParams params) {
        return IntStream.range(0, params.getChildFunctionsCount())
                .mapToObj(i -> {
                    val childForm = new FunctionForm<Integer>();

                    childForm.setDispersionCoefficient(params.getMeanFunctionForm().getDispersionCoefficient());
                    childForm.setLinearityCoefficient(params.getMeanFunctionForm().getLinearityCoefficient());

                    return childForm;
                })
                .collect(Collectors.toList());
    }

}
