package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.core.model.Sample;

import java.nio.file.Path;
import java.util.List;

public interface ExportService {

    void exportReports(Path path, String filename, List<Sample> samples);

}
