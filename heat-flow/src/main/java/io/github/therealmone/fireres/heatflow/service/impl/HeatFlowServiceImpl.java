package io.github.therealmone.fireres.heatflow.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;

import java.util.Comparator;
import java.util.List;

import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;

public class HeatFlowServiceImpl implements HeatFlowService {

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportPipeline;

    @Override
    public HeatFlowReport createReport(Sample sample) {
        val report = new HeatFlowReport(sample);
        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateSensorsCount(HeatFlowReport report, Integer sensorsCount) {
        report.getSample().getSampleProperties().getHeatFlow().setSensorCount(sensorsCount);

        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void updateBound(HeatFlowReport report, Integer bound) {

    }

    @Override
    public void updateLinearityCoefficient(HeatFlowReport report, Double linearityCoefficient) {
        report.getSample().getSampleProperties().getFireMode().setLinearityCoefficient(linearityCoefficient);

        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void updateDispersionCoefficient(HeatFlowReport report, Double dispersionCoefficient) {
        report.getSample().getSampleProperties().getFireMode().setDispersionCoefficient(dispersionCoefficient);

        reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
    }

    @Override
    public void addInterpolationPoints(HeatFlowReport report, List<InterpolationPoint> pointsToAdd) {
        val currentPoints = report.getSample().getSampleProperties().getFireMode().getInterpolationPoints();

        if (!pointsToAdd.isEmpty()) {
            currentPoints.addAll(pointsToAdd);
            currentPoints.sort(Comparator.comparing(InterpolationPoint::getTime));

            try {
                reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
            } catch (Exception e) {
                currentPoints.removeAll(pointsToAdd);
                throw e;
            }
        }
    }

    @Override
    public void removeInterpolationPoints(HeatFlowReport report, List<InterpolationPoint> pointsToRemove) {
        val currentPoints = report.getSample().getSampleProperties().getFireMode().getInterpolationPoints();

        if (currentPoints.removeIf(pointsToRemove::contains)) {
            reportPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);
        }
    }
}
