package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import lombok.val;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE;
import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class UnheatedSurfaceFirstGroupServiceImpl extends AbstractInterpolationService<UnheatedSurfaceReport, Integer>
        implements UnheatedSurfaceFirstGroupService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    public UnheatedSurfaceFirstGroupServiceImpl() {
        super(report -> report.getProperties().getFirstGroup().getFunctionForm());
    }

    @Override
    public void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount) {
        report.getProperties().getFirstGroup().setThermocoupleCount(thermocoupleCount);

        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void refreshFirstGroup(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE);
        reportPipeline.accept(report, FIRST_GROUP_MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE);
        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateLinearityCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateDispersionCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateInterpolationPoints(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(UnheatedSurfaceReport report) {
        reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedMeanTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedMeanTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedMeanTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public void addMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedThermocoupleTemperatureShift(UnheatedSurfaceReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getFirstGroup().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
