package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.report.Report;
import io.github.therealmone.fireres.core.report.ReportBuilder;
import io.github.therealmone.fireres.excel.mapper.ExcelReportMapper;
import io.github.therealmone.fireres.excel.model.Column;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ExcelReportConstructor implements ReportConstructor {

    private final GenerationProperties generationProperties;

    @Override
    @SneakyThrows
    public void construct(File outputFile) {
        val report = ReportBuilder.build(generationProperties);
        val excel = generateExcel(report);

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel(Report report) {
        val workbook = new HSSFWorkbook();
        val sheet = workbook.createSheet();
        val excelReport = ExcelReportMapper.mapToExcelReport(report);
        val columns = excelReport.getColumns();

        val headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            val headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columns.get(i).getHeader());
        }

        IntStream.range(0, excelReport.getMaxRows()).forEach(time -> {
            val row = sheet.createRow(time + 1);

            IntStream.range(0, columns.size()).forEach(i -> {
                val column = columns.get(i);
                val cell = row.createCell(i);

                cell.setCellValue(column.getValues().get(time));
            });
        });

        return workbook;
    }
}
