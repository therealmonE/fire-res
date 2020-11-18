package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.report.ExcelReport;

import java.util.Collections;
import java.util.List;

public class ExcessPressureSheetsProvider implements ExcelSheetsProvider {

    private static final String SHEET_NAME = "Избыточное давление";

    @Inject
    @Time
    private Integer time;

    @Inject
    @ExcessPressure
    private List<ExcelReport> reports;

    @Override
    public List<ExcelSheet> get() {
        return Collections.singletonList(new AbstractExcelSheet() {
            @Override
            protected Integer getTime() {
                return time;
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
