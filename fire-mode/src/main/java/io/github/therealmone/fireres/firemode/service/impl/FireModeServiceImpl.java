package io.github.therealmone.fireres.firemode.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;

import java.util.Comparator;
import java.util.List;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class FireModeServiceImpl implements FireModeService {

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportPipeline;

    @Override
    public FireModeReport createReport(Sample sample) {
        val report = new FireModeReport(sample);
        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

    @Override
    public void updateThermocoupleCount(FireModeReport report, Integer thermocoupleCount) {
        report.getSample().getSampleProperties().getFireMode().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateLinearityCoefficient(FireModeReport report, Double linearityCoefficient) {
        report.getSample().getSampleProperties().getFireMode().setLinearityCoefficient(linearityCoefficient);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateDispersionCoefficient(FireModeReport report, Double dispersionCoefficient) {
        report.getSample().getSampleProperties().getFireMode().setDispersionCoefficient(dispersionCoefficient);

        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addInterpolationPoints(FireModeReport report, List<InterpolationPoint> pointsToAdd) {
        val currentPoints = report.getSample().getSampleProperties().getFireMode().getInterpolationPoints();

        if (!pointsToAdd.isEmpty()) {
            currentPoints.addAll(pointsToAdd);
            currentPoints.sort(Comparator.comparing(InterpolationPoint::getTime));

            try {
                reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
            } catch (Exception e) {
                currentPoints.removeAll(pointsToAdd);
                throw e;
            }
        }
    }

    @Override
    public void removeInterpolationPoints(FireModeReport report, List<InterpolationPoint> pointsToRemove) {
        val currentPoints = report.getSample().getSampleProperties().getFireMode().getInterpolationPoints();

        if (currentPoints.removeIf(pointsToRemove::contains)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
