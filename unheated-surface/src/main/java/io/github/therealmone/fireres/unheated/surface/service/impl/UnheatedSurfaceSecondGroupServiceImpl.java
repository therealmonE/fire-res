package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import lombok.val;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MAX_ALLOWED_TEMPERATURE;

public class UnheatedSurfaceSecondGroupServiceImpl extends AbstractInterpolationService<UnheatedSurfaceReport, Integer>
        implements UnheatedSurfaceSecondGroupService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    public UnheatedSurfaceSecondGroupServiceImpl() {
        super(report -> report.getProperties().getSecondGroup().getFunctionForm());
    }

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getProperties().getSecondGroup().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void updateBound(UnheatedSurfaceReport report, Integer bound) {
        report.getProperties().getSecondGroup().setBound(bound);

        reportPipeline.accept(report, SECOND_GROUP_MAX_ALLOWED_TEMPERATURE);
    }

    @Override
    public void refreshSecondGroup(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, SECOND_GROUP_MAX_ALLOWED_TEMPERATURE);
        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateLinearityCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getSecondGroup().getBoundsShift().getMaxAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getSecondGroup().getBoundsShift().getMaxAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
