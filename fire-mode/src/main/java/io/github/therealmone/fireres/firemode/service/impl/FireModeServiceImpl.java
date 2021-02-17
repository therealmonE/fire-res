package io.github.therealmone.fireres.firemode.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class FireModeServiceImpl extends AbstractInterpolationService<FireModeReport> implements FireModeService {

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportPipeline;

    public FireModeServiceImpl() {
        super(FireModeReport::getProperties);
    }

    @Override
    public FireModeReport createReport(Sample sample) {
        val report = new FireModeReport(sample);
        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount) {
        report.getProperties().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateLinearityCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }
}
