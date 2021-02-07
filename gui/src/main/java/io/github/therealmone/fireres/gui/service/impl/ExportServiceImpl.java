package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.ReportConstructor;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.service.ExportService;
import lombok.val;

import java.nio.file.Path;
import java.util.List;

public class ExportServiceImpl implements ExportService {

    @Inject
    private ReportConstructor reportConstructor;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void exportReports(Path path, List<SampleTabController> sampleTabControllers) {
        sampleTabControllers.forEach(sampleTabController -> {
            val sample = sampleTabController.getSample();

            val outputFile = path.resolve(sample.getSampleProperties().getName() + ".xlsx").toFile();

            reportConstructor.construct(generationProperties.getGeneral(), sample, outputFile);
        });
    }

}
