package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.EnvironmentTemperature;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.firemode.FireModeExcelReport;

import java.util.Collections;
import java.util.List;

public class FireModeSheet extends AbstractExcelSheet {

    private static final String SHEET_NAME = "Режим пожара";

    @Inject
    private FireModeReport report;

    @Inject
    @EnvironmentTemperature
    private Integer environmentTemperature;

    public FireModeSheet() {
        super(SHEET_NAME);
    }

    @Override
    protected List<ExcelReport> createExcelReports() {
        return Collections.singletonList(new FireModeExcelReport(time, environmentTemperature, report));
    }
}
