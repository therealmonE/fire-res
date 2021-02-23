package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Sample;

import java.util.List;

public interface ExcelSheetsBuilder {

    List<ExcelSheet> build(GeneralProperties generalProperties, List<Sample> samples);

}
