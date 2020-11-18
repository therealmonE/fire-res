package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excess.pressure.factory.ExcessPressureFactory;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

@Slf4j
public class ExcessPressureReportProvider implements Provider<ExcessPressureReport> {

    @Inject
    private GenerationProperties properties;

    @Inject
    private ExcessPressureFactory factory;

    @Override
    public ExcessPressureReport get() {
        log.info("Building excess pressure report");

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
