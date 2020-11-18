package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.ExcelReports;

import java.util.List;

public class ExcessPressureSheet extends AbstractExcelSheet {

    private static final String SHEET_NAME = "Избыточное давление";

    @Inject
    @Time
    private Integer time;

    @Inject
    @ExcessPressure
    private ExcelReports reports;

    @Override
    protected Integer getTime() {
        return this.time;
    }

    @Override
    protected List<ExcelReport> getReports() {
        return this.reports.getReports();
    }

    @Override
    protected String getSheetName() {
        return SHEET_NAME;
    }
}
