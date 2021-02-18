package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.MaxAllowedPressureGenerator;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MAX_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

@Slf4j
public class MaxAllowedPressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generationProperties.getGeneral().getTime();
        val delta = report.getProperties().getDelta();

        val maxAllowedPressure = new MaxAllowedPressureGenerator(time, delta)
                .generate();

        report.setMaxAllowedPressure(maxAllowedPressure);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return MAX_ALLOWED_PRESSURE.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(PRESSURE);
    }
}
