package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_THERMOCOUPLE_BOUND;

public class UnheatedSurfaceThirdGroupServiceImpl extends AbstractInterpolationService<UnheatedSurfaceReport>
        implements UnheatedSurfaceThirdGroupService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    public UnheatedSurfaceThirdGroupServiceImpl() {
        super(report -> report.getProperties().getThirdGroup());
    }

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getProperties().getThirdGroup().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateBound(UnheatedSurfaceReport report, Integer bound) {
        report.getProperties().getThirdGroup().setBound(bound);

        reportPipeline.accept(report, THIRD_GROUP_THERMOCOUPLE_BOUND);
    }

    @Override
    public void removeInterpolationPoints(UnheatedSurfaceReport report, List<InterpolationPoint> pointsToRemove) {
        val currentPoints = report.getProperties().getThirdGroup().getInterpolationPoints();

        if (currentPoints.removeIf(pointsToRemove::contains)) {
            reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    protected void postUpdateLinearityCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }
}
