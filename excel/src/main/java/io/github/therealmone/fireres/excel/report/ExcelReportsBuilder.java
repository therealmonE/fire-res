package io.github.therealmone.fireres.excel.report;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;

import java.util.List;

public interface ExcelReportsBuilder {

    List<ExcelReport> build(GeneralProperties generalProperties, Report report);

}
