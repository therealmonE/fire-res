package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MIN_ALLOWED_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleMinAllowedPressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichMinAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val oldMinAllowedPressure = report.getMinAllowedPressure();

        report.getProperties().setDelta(1.0);
        reportEnrichPipeline.accept(report, MIN_ALLOWED_PRESSURE);

        val newMinAllowedPressure = report.getMinAllowedPressure();

        assertNotEquals(oldMinAllowedPressure, newMinAllowedPressure);
    }

}
