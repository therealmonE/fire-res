package io.github.therealmone.fireres.unheated.surface.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceSecondaryGroupProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

public abstract class SecondaryThermocoupleBoundEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val groupProperties = getGroupProperties(report);
        val time = generationProperties.getGeneral().getTime();

        val thermocoupleBound = new UnheatedSurfaceThermocoupleBound(
                constantFunction(time, groupProperties.getBound()).getValue());

        getGroup(report).setThermocoupleBound(thermocoupleBound);

    }

    protected abstract UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report);

    protected abstract UnheatedSurfaceSecondaryGroupProperties getGroupProperties(UnheatedSurfaceReport report);

}
