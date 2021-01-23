package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class HeatFlowReportProvider implements Provider<HeatFlowReport> {

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> enrichPipeline;

    @Override
    public HeatFlowReport get() {
        log.info("Building heat flow report");

        val report = new HeatFlowReport();

        enrichPipeline.accept(report);

        log.info("Heat flow report was built successfully");

        return report;
    }
}
