package io.github.therealmone.fireres.unheated.surface.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.report.UnheatedSurfaceReportEnrichType.SAMPLES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SamplesEnrichTest {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Test
    public void enrichSampleBound() {
        val report = new UnheatedSurfaceReport();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();

        reportEnrichPipeline.accept(report, SAMPLES);
        val newSamples = report.getSamples();

        assertNotEquals(oldSamples, newSamples);
    }

}
