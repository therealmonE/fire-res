package io.github.therealmone.fireres.excess.pressure.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.pipeline.report.ExcessPressureReportEnrichType.SAMPLES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SamplesEnrichTest {

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichSamples() {
        val report = new ExcessPressureReport();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();

        reportEnrichPipeline.accept(report, SAMPLES);
        val newSamples = report.getSamples();

        assertNotEquals(oldSamples, newSamples);
    }

}
