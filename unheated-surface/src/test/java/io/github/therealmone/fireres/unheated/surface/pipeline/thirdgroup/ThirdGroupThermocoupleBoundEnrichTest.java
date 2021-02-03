package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_THERMOCOUPLE_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class ThirdGroupThermocoupleBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldThirdGroupThermocoupleBound = report.getThirdGroup().getThermocoupleBound();
        val oldThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val oldThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        generationProperties.getSampleById(sample.getId()).getUnheatedSurface().getThirdGroup().setBound(400);
        reportEnrichPipeline.accept(report, THIRD_GROUP_THERMOCOUPLE_BOUND);

        val newThirdGroupThermocoupleBound = report.getThirdGroup().getThermocoupleBound();
        val newThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val newThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        assertNotEquals(oldThirdGroupThermocoupleBound, newThirdGroupThermocoupleBound);
        assertNotEquals(oldThirdGroupMeanTemperature, newThirdGroupMeanTemperature);
        assertNotEquals(oldThirdGroupThermocoupleTemperatures, newThirdGroupThermocoupleTemperatures);
    }

}
