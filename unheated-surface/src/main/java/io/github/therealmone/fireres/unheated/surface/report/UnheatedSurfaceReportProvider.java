package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class UnheatedSurfaceReportProvider implements Provider<UnheatedSurfaceReport> {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> enrichPipeline;

    @Override
    public UnheatedSurfaceReport get() {
        log.info("Building unheated surface report");

        val report = new UnheatedSurfaceReport();

        enrichPipeline.accept(report);

        log.info("Unheated surface report was built successfully");

        return report;
    }

}
