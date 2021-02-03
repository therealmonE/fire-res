package io.github.therealmone.fireres.unheated.surface.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.unheated.surface.generator.UnheatedSurfaceThermocoupleBoundGenerator;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

public abstract class PrimaryThermocoupleBoundEnricher implements ReportEnricher<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(UnheatedSurfaceReport report) {
        val time = generationProperties.getGeneral().getTime();
        val t0 = generationProperties.getGeneral().getEnvironmentTemperature();

        val thermocoupleBound = new UnheatedSurfaceThermocoupleBoundGenerator(time, t0)
                .generate();

        getGroup(report).setThermocoupleBound(thermocoupleBound);
    }

    protected abstract UnheatedSurfaceGroup getGroup(UnheatedSurfaceReport report);

}
