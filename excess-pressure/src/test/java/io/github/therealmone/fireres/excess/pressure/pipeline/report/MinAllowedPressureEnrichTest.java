package io.github.therealmone.fireres.excess.pressure.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.pipeline.report.ExcessPressureReportEnrichType.MIN_ALLOWED_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class MinAllowedPressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichMinAllowedPressure() {
        val report = new ExcessPressureReport();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();
        val oldMinAllowedPressure = report.getMinAllowedPressure();

        generationProperties.getGeneral().getExcessPressure().setDelta(1.0);

        reportEnrichPipeline.accept(report, MIN_ALLOWED_PRESSURE);
        val newSamples = report.getSamples();
        val newMinAllowedPressure = report.getMinAllowedPressure();

        assertNotEquals(oldSamples, newSamples);
        assertNotEquals(oldMinAllowedPressure, newMinAllowedPressure);
    }

}
