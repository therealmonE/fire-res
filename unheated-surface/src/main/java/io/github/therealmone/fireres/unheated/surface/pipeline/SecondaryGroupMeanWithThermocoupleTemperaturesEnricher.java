package io.github.therealmone.fireres.unheated.surface.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.factory.MeanFunctionFactory;
import io.github.therealmone.fireres.core.generator.MeanWithChildFunctionGenerationParameters;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceGeneratorStrategy;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleTemperature;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.stream.Collectors;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public abstract class SecondaryGroupMeanWithThermocoupleTemperaturesEnricher
        implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private MeanFunctionFactory meanFunctionFactory;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val group = getGroup(report);
        val thermocoupleBound = group.getThermocoupleBound();
        val zeroBound = constantFunction(time, 0);
        val groupProperties = getGroupProperties(report);

        val meanWithChildFunctions = meanFunctionFactory
                .meanWithChildFunctions(MeanWithChildFunctionGenerationParameters.builder()
                        .meanFunctionForm(groupProperties.getFunctionForm())
                        .meanLowerBound(zeroBound)
                        .meanUpperBound(thermocoupleBound)
                        .childFunctionsCount(groupProperties.getThermocoupleCount())
                        .childLowerBound(zeroBound)
                        .childUpperBound(thermocoupleBound)
                        .strategy(new UnheatedSurfaceGeneratorStrategy())
                        .build());

        group.setMeanTemperature(new UnheatedSurfaceMeanTemperature(meanWithChildFunctions.getFirst().getValue()));
        group.setThermocoupleTemperatures(meanWithChildFunctions.getSecond().stream()
                .map(child -> new UnheatedSurfaceThermocoupleTemperature(child.getValue()))
                .collect(Collectors.toList()));
    }

    protected abstract UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report);

    protected abstract UnheatedSurfaceGroupProperties getGroupProperties(UnheatedSurfaceReport report);
}
