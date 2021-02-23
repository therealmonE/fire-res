package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Sample;

import java.io.File;
import java.util.List;

public interface ReportConstructor {

    void construct(GeneralProperties generalProperties, List<Sample> samples, File outputFile);

}
