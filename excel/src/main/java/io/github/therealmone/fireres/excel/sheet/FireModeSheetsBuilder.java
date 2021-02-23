package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FireModeSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Режим пожара";

    @Inject
    @FireMode
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, List<Sample> samples) {
        val time = generalProperties.getTime();

        val reports = samples.stream()
                .map(sample -> sample.getReportByClass(FireModeReport.class))
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
