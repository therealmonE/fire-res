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

import static io.github.therealmone.fireres.excess.pressure.pipeline.sample.ExcessPressureSampleEnrichType.SAMPLE_BASE_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleBasePressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<ExcessPressureReport, ExcessPressureSample> sampleEnrichPipeline;

    @Test
    public void enrichBasePressure() {
        val report = new ExcessPressureReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldBasePressure = sample.getBasePressure();

        generationProperties.getSampleById(sample.getId()).getExcessPressure().setBasePressure(10000.0);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_BASE_PRESSURE);
        val newBasePressure = sample.getBasePressure();

        assertNotEquals(oldBasePressure, newBasePressure);
    }

}
