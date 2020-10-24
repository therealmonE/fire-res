package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.report.Report;
import io.github.therealmone.fireres.core.report.ReportBuilder;
import io.github.therealmone.fireres.excel.mapper.ExcelReportMapper;
import io.github.therealmone.fireres.excel.model.Column;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.excel.chart.FireResChart.generateChart;


@RequiredArgsConstructor
@Slf4j
public class ExcelReportConstructor implements ReportConstructor {

    private final GenerationProperties generationProperties;

    @Override
    @SneakyThrows
    public void construct(File outputFile) {
        log.info("Writing excel report to: {}", outputFile.getAbsolutePath());
        val report = ReportBuilder.build(generationProperties);

        try (val excel = generateExcel(report);
             val outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel(Report report) {
        val workbook = new XSSFWorkbook();
        val sheet = workbook.createSheet();
        val excelReport = ExcelReportMapper.mapToExcelReport(report);
        val time = excelReport.getTime();
        val columns = excelReport.getColumns();

        generateHeaders(sheet, columns);
        generateData(sheet, time, columns);
        generateChart(sheet, time, columns);

        return workbook;
    }

    private void generateData(XSSFSheet sheet, Integer maxRows, List<Column> columns) {
        IntStream.range(0, maxRows).forEach(time -> {
            val row = sheet.createRow(time + 1);

            IntStream.range(0, columns.size()).forEach(i -> {
                val column = columns.get(i);
                val cell = row.createCell(i);

                cell.setCellValue(column.getValues().get(time));
            });
        });
    }

    private void generateHeaders(XSSFSheet sheet, List<Column> columns) {
        val headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            val headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columns.get(i).getHeader());
        }
    }

}
