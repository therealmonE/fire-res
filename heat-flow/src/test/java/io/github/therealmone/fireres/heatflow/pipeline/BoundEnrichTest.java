package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.heatflow.HeatFlowGuiceRunner;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(HeatFlowGuiceRunner.class)
public class BoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Test
    public void enrichSampleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val oldBound = report.getBound();

        report.getProperties().setBound(0.5);
        report.getProperties().getFunctionForm().getInterpolationPoints().clear();
        reportEnrichPipeline.accept(report, BOUND);

        val newBound = report.getBound();

        assertNotEquals(oldBound, newBound);
    }

}
