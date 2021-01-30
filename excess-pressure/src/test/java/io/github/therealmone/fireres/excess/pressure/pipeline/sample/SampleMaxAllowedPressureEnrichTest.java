package io.github.therealmone.fireres.excess.pressure.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_MAX_ALLOWED_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleMaxAllowedPressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<ExcessPressureReport, ExcessPressureSample> sampleEnrichPipeline;

    @Test
    public void enrichMaxAllowedPressure() {
        val report = new ExcessPressureReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldMaxAllowedPressure = sample.getMaxAllowedPressure();

        generationProperties.getSampleById(sample.getId()).getExcessPressure().setDelta(1.0);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_MAX_ALLOWED_PRESSURE);
        val newMaxAllowedPressure = sample.getMaxAllowedPressure();

        assertNotEquals(oldMaxAllowedPressure, newMaxAllowedPressure);
    }

}
