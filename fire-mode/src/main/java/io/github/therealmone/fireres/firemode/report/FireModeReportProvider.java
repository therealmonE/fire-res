package io.github.therealmone.fireres.firemode.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class FireModeReportProvider implements Provider<FireModeReport> {

    @Inject
    private ReportEnrichPipeline<FireModeReport> enrichPipeline;

    @Override
    public FireModeReport get() {
        log.info("Building fire mode report");

        val report = new FireModeReport();

        enrichPipeline.accept(report);

        log.info("Fire mode report was built successfully");

        return report;
    }
}
