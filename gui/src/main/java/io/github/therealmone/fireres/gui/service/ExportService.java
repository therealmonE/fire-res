package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.SampleTabController;

import java.nio.file.Path;
import java.util.List;

public interface ExportService {

    void exportReports(Path path, List<SampleTabController> sampleTabControllers);

}
