package io.github.therealmone.fireres.unheated.surface.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.unheated.surface.config.SecondaryGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.unheated.surface.model.Group;
import io.github.therealmone.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import io.github.therealmone.fireres.unheated.surface.model.MaxAllowedThermocoupleTemperature;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public abstract class SecondaryGroupMaxAllowedTemperatureEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val groupProperties = getGroupProperties(report);
        val time = generationProperties.getGeneral().getTime();

        val maxAllowedTemperature = constantFunction(time, groupProperties.getBound()).getValue();

        getGroup(report).setMaxAllowedThermocoupleTemperature(new MaxAllowedThermocoupleTemperature(maxAllowedTemperature));
        getGroup(report).setMaxAllowedMeanTemperature(new MaxAllowedMeanTemperature(maxAllowedTemperature));
    }

    protected abstract Group getGroup(UnheatedSurfaceReport report);

    protected abstract SecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report);

}
