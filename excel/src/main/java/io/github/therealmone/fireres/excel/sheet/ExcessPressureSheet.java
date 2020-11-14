package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.pressure.ExcessPressureExelReport;

import java.util.Collections;
import java.util.List;

public class ExcessPressureSheet extends AbstractExcelSheet<ExcessPressureReport> {

    private static final String SHEET_NAME = "График избыточного давления";
    private final Double basePressure;

    public ExcessPressureSheet(ExcessPressureReport report, Integer time, Double basePressure) {
        super(SHEET_NAME, time, report);
        this.basePressure = basePressure;
    }

    @Override
    protected List<ExcelReport> createExcelReports(ExcessPressureReport report) {
        return Collections.singletonList(new ExcessPressureExelReport(time, report, basePressure));
    }
}
