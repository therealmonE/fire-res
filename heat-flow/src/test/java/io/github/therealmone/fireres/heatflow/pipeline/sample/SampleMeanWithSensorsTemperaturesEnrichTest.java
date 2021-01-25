package io.github.therealmone.fireres.heatflow.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.heatflow.GuiceRunner;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.heatflow.pipeline.sample.HeatFlowSampleEnrichType.SAMPLE_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleMeanWithSensorsTemperaturesEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<HeatFlowReport, HeatFlowSample> sampleEnrichPipeline;

    @Test
    public void enrichSampleMeanWithSensors() {
        val report = new HeatFlowReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldSampleMeanTemperature = sample.getMeanTemperature();
        val oldSampleSensorsTemperatures = sample.getSensorTemperatures();

        generationProperties.getSamples().get(0).getHeatFlow().setBound(500);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_BOUND);
        val newSampleMeanTemperature = sample.getMeanTemperature();
        val newSampleSensorsTemperatures = sample.getSensorTemperatures();

        assertNotEquals(oldSampleMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSampleSensorsTemperatures, newSampleSensorsTemperatures);
    }

}
