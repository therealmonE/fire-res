package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class FurnaceTemperatureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichFurnaceTemperature() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = fireModeService.createReport(sample);

        val oldFurnaceTemperature = report.getFurnaceTemperature();

        generationProperties.getGeneral().setEnvironmentTemperature(24);

        reportEnrichPipeline.accept(report, FireModeReportEnrichType.FURNACE_TEMPERATURE);
        val newFurnaceTemperature = report.getFurnaceTemperature();

        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
    }

}
