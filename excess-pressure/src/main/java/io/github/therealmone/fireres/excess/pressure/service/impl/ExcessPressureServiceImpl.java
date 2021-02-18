package io.github.therealmone.fireres.excess.pressure.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MAX_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MIN_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

public class ExcessPressureServiceImpl implements ExcessPressureService {

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportPipeline;

    @Override
    public ExcessPressureReport createReport(Sample sample) {
        val report = new ExcessPressureReport(sample);
        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateBasePressure(ExcessPressureReport report, Double basePressure) {
        report.getProperties().setBasePressure(basePressure);

        reportPipeline.accept(report, BASE_PRESSURE);
    }

    @Override
    public void updateDelta(ExcessPressureReport report, Double delta) {
        report.getProperties().setDelta(delta);

        reportPipeline.accept(report, MIN_ALLOWED_PRESSURE);
        reportPipeline.accept(report, MAX_ALLOWED_PRESSURE);
    }

    @Override
    public void updateDispersionCoefficient(ExcessPressureReport report, Double dispersionCoefficient) {
        report.getProperties().setDispersionCoefficient(dispersionCoefficient);

        reportPipeline.accept(report, PRESSURE);
    }

}
