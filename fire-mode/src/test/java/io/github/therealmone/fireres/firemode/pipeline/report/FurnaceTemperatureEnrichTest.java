package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.FURNACE_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class FurnaceTemperatureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichFurnaceTemperature() {
        val report = new FireModeReport();

        reportEnrichPipeline.accept(report);
        val oldFurnaceTemperature = report.getFurnaceTemperature();

        generationProperties.getGeneral().setEnvironmentTemperature(24);

        reportEnrichPipeline.accept(report, FURNACE_TEMPERATURE);
        val newFurnaceTemperature = report.getFurnaceTemperature();

        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
    }

}
