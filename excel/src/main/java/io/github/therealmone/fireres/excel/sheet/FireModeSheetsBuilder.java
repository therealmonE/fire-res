package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.Collections;
import java.util.List;

public class FireModeSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Режим пожара";

    @Inject
    @FireMode
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, Report report) {
        val time = generalProperties.getTime();
        val reports = excelReportsBuilder.build(generalProperties, (FireModeReport) report);

        return Collections.singletonList(new ExcelSheet(time, reports, SHEET_NAME));
    }
}
