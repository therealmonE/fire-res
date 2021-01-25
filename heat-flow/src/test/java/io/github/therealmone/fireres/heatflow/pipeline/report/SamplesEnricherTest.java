package io.github.therealmone.fireres.heatflow.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.heatflow.GuiceRunner;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.heatflow.pipeline.report.HeatFlowReportEnrichType.SAMPLES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SamplesEnricherTest {

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Test
    public void enrichSamples() {
        val report = new HeatFlowReport();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();

        reportEnrichPipeline.accept(report, SAMPLES);
        val newSamples = report.getSamples();

        assertNotEquals(oldSamples, newSamples);
    }

}
