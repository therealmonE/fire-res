package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;

import java.util.List;

public interface ExcelSheetsBuilder {

    List<ExcelSheet> build(GeneralProperties generalProperties, Report report);

}
