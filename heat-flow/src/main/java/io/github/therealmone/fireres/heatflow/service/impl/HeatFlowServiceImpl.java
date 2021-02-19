package io.github.therealmone.fireres.heatflow.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;

import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.BOUND;
import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;

public class HeatFlowServiceImpl extends AbstractInterpolationService<HeatFlowReport, Double> implements HeatFlowService {

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportPipeline;

    public HeatFlowServiceImpl() {
        super(HeatFlowReport::getProperties);
    }

    @Override
    public HeatFlowReport createReport(Sample sample) {
        val report = new HeatFlowReport(sample);
        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateSensorsCount(HeatFlowReport report, Integer sensorsCount) {
        report.getProperties().setSensorCount(sensorsCount);

        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void updateBound(HeatFlowReport report, Double bound) {
        report.getProperties().setBound(bound);

        reportPipeline.accept(report, BOUND);
    }

    @Override
    protected void postUpdateLinearityCoefficient(HeatFlowReport report) {
        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(HeatFlowReport report) {
        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(HeatFlowReport report) {
        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }
}
