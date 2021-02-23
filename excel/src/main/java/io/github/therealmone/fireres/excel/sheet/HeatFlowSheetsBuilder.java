package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.annotation.HeatFlow;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public class HeatFlowSheetsBuilder implements ExcelSheetsBuilder {

    private static final String SHEET_NAME = "Тепловой поток - %s";

    @Inject
    @HeatFlow
    private ExcelReportsBuilder excelReportsBuilder;

    @Override
    public List<ExcelSheet> build(GeneralProperties generalProperties, List<Sample> samples) {
        val time = generalProperties.getTime();

        return samples.stream()
                .flatMap(sample -> {
                    val report = sample.getReportByClass(HeatFlowReport.class);

                    if (report.isPresent()) {
                        val excelReports = excelReportsBuilder.build(generalProperties, singletonList(report.get()));
                        val sheetName = String.format(SHEET_NAME, sample.getSampleProperties().getName());

                        return Stream.of(new ExcelSheet(time, excelReports, sheetName));
                    } else {
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }
}