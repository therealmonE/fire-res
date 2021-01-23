package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class ExcessPressureReportProvider implements Provider<ExcessPressureReport> {

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> enrichPipeline;

    @Override
    public ExcessPressureReport get() {
        log.info("Building excess pressure report");

        val report = new ExcessPressureReport();

        enrichPipeline.accept(report);

        log.info("Excess pressure report was built successfully");

        return report;
    }

}
