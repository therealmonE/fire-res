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

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.MAX_ALLOWED_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleMaxAllowedPressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichMaxAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val oldMaxAllowedPressure = report.getMaxAllowedPressure();

        sample.getSampleProperties().getExcessPressure().setDelta(1.0);

        reportEnrichPipeline.accept(report, MAX_ALLOWED_PRESSURE);
        val newMaxAllowedPressure = report.getMaxAllowedPressure();

        assertNotEquals(oldMaxAllowedPressure, newMaxAllowedPressure);
    }

}
