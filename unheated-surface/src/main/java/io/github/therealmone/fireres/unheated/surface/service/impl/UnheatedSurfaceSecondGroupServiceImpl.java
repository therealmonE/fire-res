package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import lombok.val;

import java.util.Comparator;
import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class UnheatedSurfaceSecondGroupServiceImpl implements UnheatedSurfaceSecondGroupService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getSample().getSampleProperties().getUnheatedSurface().getSecondGroup().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateBound(UnheatedSurfaceReport report, Integer bound) {
        report.getSample().getSampleProperties().getUnheatedSurface().getSecondGroup().setBound(bound);

        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateLinearityCoefficient(UnheatedSurfaceReport report, Double linearityCoefficient) {
        report.getSample().getSampleProperties().getUnheatedSurface().getSecondGroup().setLinearityCoefficient(linearityCoefficient);

        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateDispersionCoefficient(UnheatedSurfaceReport report, Double dispersionCoefficient) {
        report.getSample().getSampleProperties().getUnheatedSurface().getSecondGroup().setDispersionCoefficient(dispersionCoefficient);

        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addInterpolationPoints(UnheatedSurfaceReport report, List<InterpolationPoint> pointsToAdd) {
        val currentPoints = report.getSample().getSampleProperties().getUnheatedSurface()
                .getSecondGroup().getInterpolationPoints();

        if (!pointsToAdd.isEmpty()) {
            currentPoints.addAll(pointsToAdd);
            currentPoints.sort(Comparator.comparing(InterpolationPoint::getTime));

            reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public void removeInterpolationPoints(UnheatedSurfaceReport report, List<InterpolationPoint> pointsToRemove) {
        val currentPoints = report.getSample().getSampleProperties().getUnheatedSurface()
                .getSecondGroup().getInterpolationPoints();

        if (currentPoints.removeIf(pointsToRemove::contains)) {
            reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
