package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.excess.pressure.generator.PressureGenerator;
import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.PRESSURE;

@Slf4j
public class PressureEnricher implements ReportEnricher<ExcessPressureReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(ExcessPressureReport report) {
        val time = generationProperties.getGeneral().getTime();

        val minAllowedPressure = new MinAllowedPressure(report.getMinAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedPressureShift()));

        val maxAllowedPressure = new MaxAllowedPressure(report.getMaxAllowedPressure()
                .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedPressureShift()));

        val dispersion = report.getProperties().getDispersionCoefficient();

        val pressure = new PressureGenerator(
                time, minAllowedPressure, maxAllowedPressure, dispersion)
                .generate();

        report.setPressure(pressure);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return PRESSURE.equals(enrichType);
    }

}
