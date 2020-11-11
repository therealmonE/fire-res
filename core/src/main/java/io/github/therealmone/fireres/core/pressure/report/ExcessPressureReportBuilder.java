package io.github.therealmone.fireres.core.pressure.report;

import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.report.ReportBuilder;
import io.github.therealmone.fireres.core.pressure.ExcessPressureFactory;
import io.github.therealmone.fireres.core.pressure.model.ExcessPressureSample;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

@Slf4j
public class ExcessPressureReportBuilder implements ReportBuilder<ExcessPressureReport> {

    @Override
    public ExcessPressureReport build(GenerationProperties properties) {
        log.info("Building excess pressure report");
        val factory = new ExcessPressureFactory(properties);

        val minAllowedPressure = factory.minAllowedPressure();
        val maxAllowedPressure = factory.maxAllowedPressure();
        val samples = properties.getSamples().stream()
                .map(sample -> new ExcessPressureSample(
                        factory.excessPressure(minAllowedPressure, maxAllowedPressure)))
                .collect(Collectors.toList());

        log.info("Excess pressure report was built successfully");
        return ExcessPressureReport.builder()
                .maxAllowedPressure(maxAllowedPressure)
                .minAllowedPressure(minAllowedPressure)
                .samples(samples)
                .build();
    }

}
