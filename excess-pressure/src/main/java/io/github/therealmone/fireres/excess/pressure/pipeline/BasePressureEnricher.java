package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;

@Slf4j
public class BasePressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val sample = report.getSample();
        val sampleProperties = generationProperties.getSampleById(sample.getId());

        report.setBasePressure(sampleProperties.getExcessPressure().getBasePressure());
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return BASE_PRESSURE.equals(enrichType);
    }
}
