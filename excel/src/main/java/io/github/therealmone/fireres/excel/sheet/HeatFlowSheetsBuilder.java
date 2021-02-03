package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excel.annotation.HeatFlow;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;

import java.util.Collections;
import java.util.List;

public class HeatFlowSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Изм. плотности теплового потока";

    @Inject
    @HeatFlow
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, Report report) {
        val time = generalProperties.getTime();
        val reports = excelReportsBuilder.build(generalProperties, (HeatFlowReport) report);

        return Collections.singletonList(new ExcelSheet(time, reports, SHEET_NAME));
    }
}