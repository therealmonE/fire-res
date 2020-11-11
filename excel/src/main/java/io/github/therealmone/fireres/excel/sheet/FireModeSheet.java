package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.core.firemode.report.FireModeReport;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.firemode.FireModeExcelReport;

import java.util.Collections;
import java.util.List;

public class FireModeSheet extends AbstractExcelSheet<FireModeReport> {

    private static final String SHEET_NAME = "Режим пожара";

    private final Integer environmentTemperature;

    public FireModeSheet(FireModeReport report, Integer time, Integer environmentTemperature) {
        super(SHEET_NAME, time, report);
        this.environmentTemperature = environmentTemperature;
    }

    @Override
    protected List<ExcelReport> createExcelReports(FireModeReport report) {
        return Collections.singletonList(new FireModeExcelReport(time, environmentTemperature, report));
    }
}
