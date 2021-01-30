package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.annotation.HeatFlow;
import io.github.therealmone.fireres.excel.report.ExcelReport;

import java.util.Collections;
import java.util.List;

public class HeatFlowSheetsProvider implements ExcelSheetsProvider {

    private static final String SHEET_NAME = "Изм. плотности теплового потока";

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    @HeatFlow
    private List<ExcelReport> reports;

    @Override
    public List<ExcelSheet> get() {
        return Collections.singletonList(new AbstractExcelSheet() {
            @Override
            protected Integer getTime() {
                return generationProperties.getGeneral().getTime();
            }

            @Override
            protected List<ExcelReport> getReports() {
                return reports;
            }

            @Override
            protected String getSheetName() {
                return SHEET_NAME;
            }
        });
    }
}