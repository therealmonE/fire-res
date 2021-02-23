package io.github.therealmone.fireres.excel;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

@RunWith(GuiceRunner.class)
public class ExcelReportConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Inject
    private ReportConstructor reportConstructor;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private GenerationProperties generationProperties;

    @Test
    public void construct() throws IOException {
        val sample = new Sample(generationProperties.getSamples().get(0));

        fireModeService.createReport(sample);
        excessPressureService.createReport(sample);
        unheatedSurfaceService.createReport(sample);
        heatFlowService.createReport(sample);

        val file = temporaryFolder.newFile("test.xls");

        reportConstructor.construct(generationProperties.getGeneral(), Collections.singletonList(sample), file);
    }

}