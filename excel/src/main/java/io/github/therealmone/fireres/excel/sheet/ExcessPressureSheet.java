package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.BasePressure;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.pressure.ExcessPressureExelReport;

import java.util.Collections;
import java.util.List;

public class ExcessPressureSheet extends AbstractExcelSheet {

    private static final String SHEET_NAME = "Избыточное давление";

    @Inject
    @BasePressure
    private Double basePressure;

    @Inject
    @Time
    private Integer time;

    @Inject
    private ExcessPressureReport report;

    public ExcessPressureSheet() {
        super(SHEET_NAME);
    }

    @Override
    protected List<ExcelReport> createExcelReports() {
        return Collections.singletonList(new ExcessPressureExelReport(time, report, basePressure));
    }
}
