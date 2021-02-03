package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.heatflow.GuiceRunner;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class MeanWithSensorsTemperaturesEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private ReportEnrichPipeline<HeatFlowReport> reportEnrichPipeline;

    @Test
    public void enrichSampleMeanWithSensors() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val oldMeanTemperature = report.getMeanTemperature();
        val oldSensorsTemperatures = report.getSensorTemperatures();

        generationProperties.getSamples().get(0).getHeatFlow().setBound(500);
        reportEnrichPipeline.accept(report, MEAN_WITH_SENSORS_TEMPERATURES);

        val newSampleMeanTemperature = report.getMeanTemperature();
        val newSampleSensorsTemperatures = report.getSensorTemperatures();

        assertNotEquals(oldMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSensorsTemperatures, newSampleSensorsTemperatures);
    }

}
