package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.SAMPLES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SamplesEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichSamples() {
        val report = new FireModeReport();

        generationProperties.getSamples().get(0).getFireMode().getInterpolationPoints().clear();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();

        reportEnrichPipeline.accept(report, SAMPLES);
        val newSamples = report.getSamples();

        assertNotEquals(oldSamples, newSamples);
    }

}
