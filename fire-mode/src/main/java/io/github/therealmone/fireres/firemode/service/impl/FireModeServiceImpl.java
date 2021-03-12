package io.github.therealmone.fireres.firemode.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.service.impl.AbstractInterpolationService;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;

public class FireModeServiceImpl extends AbstractInterpolationService<FireModeReport, Integer> implements FireModeService {

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportPipeline;

    public FireModeServiceImpl() {
        super(report -> report.getProperties().getFunctionForm());
    }

    @Override
    public FireModeReport createReport(UUID reportId, Sample sample) {
        val report = new FireModeReport(reportId, sample);
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

    @Override
    protected void postUpdateChildFunctionsDeltaCoefficient(FireModeReport report) {
        reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
    }

    @Override
    public void addMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMaxAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMaxAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }

    @Override
    public void addMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedTemperatureShift();

        currentShift.add(shift);

        try {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        } catch (Exception e) {
            currentShift.remove(shift);
            throw e;
        }
    }

    @Override
    public void removeMinAllowedTemperatureShift(FireModeReport report, IntegerPoint shift) {
        val currentShift = report.getProperties().getBoundsShift().getMinAllowedTemperatureShift();

        if (currentShift.remove(shift)) {
            reportPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        }
    }
}
