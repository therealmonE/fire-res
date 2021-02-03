package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.Collections;
import java.util.List;

public class ExcessPressureSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Избыточное давление";

    @Inject
    @ExcessPressure
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, Report report) {
        val time = generalProperties.getTime();
        val excelReports = excelReportsBuilder.build(generalProperties, (ExcessPressureReport) report);

        return Collections.singletonList(new ExcelSheet(time, excelReports, SHEET_NAME));
    }
}
