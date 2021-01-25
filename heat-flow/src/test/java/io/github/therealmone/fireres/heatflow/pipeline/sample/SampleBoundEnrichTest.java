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
public class SampleBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<HeatFlowReport, HeatFlowSample> sampleEnrichPipeline;

    @Test
    public void enrichSampleBound() {
        val report = new HeatFlowReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldSampleBound = sample.getBound();

        generationProperties.getSamples().get(0).getHeatFlow().setBound(500);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_BOUND);
        val newSampleBound = sample.getBound();

        assertNotEquals(oldSampleBound, newSampleBound);
    }

}
