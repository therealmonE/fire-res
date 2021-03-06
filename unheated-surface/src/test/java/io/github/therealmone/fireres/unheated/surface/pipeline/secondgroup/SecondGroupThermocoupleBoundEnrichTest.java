package io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceGuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MAX_ALLOWED_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(UnheatedSurfaceGuiceRunner.class)
public class SecondGroupThermocoupleBoundEnrichTest {

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

        val oldSecondGroupThermocoupleBound = report.getSecondGroup().getMaxAllowedThermocoupleTemperature();
        val oldSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        report.getProperties().getSecondGroup().setBound(400);
        reportEnrichPipeline.accept(report, SECOND_GROUP_MAX_ALLOWED_TEMPERATURE);

        val newSecondGroupThermocoupleBound = report.getSecondGroup().getMaxAllowedThermocoupleTemperature();
        val newSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupThermocoupleBound, newSecondGroupThermocoupleBound);
        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
