package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.heatflow.factory.HeatFlowFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HeatFlowReportProvider  implements Provider<HeatFlowReport> {
    @Inject
    private HeatFlowFactory heatFlowFactory;

    @Inject
    private GenerationProperties properties;

    @Override
    public HeatFlowReport get() {
        log.info("Building heat flow report");

        val samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(sample -> heatFlowFactory.heatFlowSample(sample))
                .collect(Collectors.toList());

        log.info("Heat flow report was built successfully");
        return new HeatFlowReport(samples);
    }
}
