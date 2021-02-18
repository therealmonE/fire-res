package io.github.therealmone.fireres.excess.pressure.pipeline;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;

@Slf4j
public class BasePressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Override
    public void enrich(ExcessPressureReport report) {
        report.setBasePressure(report.getProperties().getBasePressure());
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return BASE_PRESSURE.equals(enrichType);
    }
}
