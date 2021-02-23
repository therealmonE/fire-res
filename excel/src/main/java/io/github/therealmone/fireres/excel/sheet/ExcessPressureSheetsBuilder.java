package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExcessPressureSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Избыточное давление";

    @Inject
    @ExcessPressure
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, List<Sample> samples) {
        val time = generalProperties.getTime();

        val reports = samples.stream()
                .map(sample -> sample.getReportByClass(ExcessPressureReport.class))
                .filter(Optional::isPresent)
                .map(optional -> (Report) optional.get())
                .collect(Collectors.toList());

        if (!reports.isEmpty()) {
            val excelReports = excelReportsBuilder.build(generalProperties, reports);

            return Collections.singletonList(new ExcelSheet(time, excelReports, SHEET_NAME));
        } else {
            return Collections.emptyList();
        }
    }
}
