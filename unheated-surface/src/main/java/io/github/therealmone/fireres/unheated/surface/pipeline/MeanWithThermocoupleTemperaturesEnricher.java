package io.github.therealmone.fireres.unheated.surface.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.FunctionsGenerationBounds;
import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceGenerationStrategy;
import io.github.therealmone.fireres.unheated.surface.model.Group;
import io.github.therealmone.fireres.unheated.surface.model.MeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public abstract class MeanWithThermocoupleTemperaturesEnricher
        implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FunctionsGenerationService functionsGenerationService;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val group = getGroup(report);

        val thermocoupleBound = new IntegerPointSequence(group.getMaxAllowedThermocoupleTemperature()
                .getShiftedValue(getThermocoupleBoundShift(report)));

        val meanBound = new IntegerPointSequence(group.getMaxAllowedMeanTemperature()
                .getShiftedValue(getMeanBoundShift(report)));

        val zeroBound = constantFunction(time, 0);

        val meanWithChildFunctions = functionsGenerationService
                .meanWithChildFunctions(FunctionsGenerationParams.builder()
                        .meanFunctionForm(getFunctionForm(report))
                        .meanBounds(new FunctionsGenerationBounds(zeroBound, meanBound))
                        .childrenBounds(new FunctionsGenerationBounds(zeroBound, thermocoupleBound))
                        .childFunctionsCount(getThermocoupleCount(report))
                        .strategy(new UnheatedSurfaceGenerationStrategy())
                        .build());

        group.setMeanTemperature(new MeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        group.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new ThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    protected abstract Group getGroup(UnheatedSurfaceReport report);

    protected abstract FunctionForm<Integer> getFunctionForm(UnheatedSurfaceReport report);

    protected abstract BoundShift<IntegerPoint> getMeanBoundShift(UnheatedSurfaceReport report);

    protected abstract BoundShift<IntegerPoint> getThermocoupleBoundShift(UnheatedSurfaceReport report);

    protected abstract Integer getThermocoupleCount(UnheatedSurfaceReport report);

}
