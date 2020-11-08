package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.report.Report;
import io.github.therealmone.fireres.core.report.ReportBuilder;
import io.github.therealmone.fireres.excel.mapper.ExcelReportMapper;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.style.data.DataCellStyles;
import io.github.therealmone.fireres.excel.style.data.HeaderCellStyles;
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

    public static final String TIMES_NEW_ROMAN = "Times New Roman";

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

        generateHeaders(sheet, columns, new HeaderCellStyles(workbook));
        generateData(sheet, time, columns, new DataCellStyles(workbook));
        generateChart(sheet, time, columns);

        return workbook;
    }

    private void generateData(XSSFSheet sheet, Integer maxRows, List<Column> columns, DataCellStyles cellStyles) {
        IntStream.range(0, maxRows).forEach(time -> {
            val row = sheet.createRow(time + 1);

            IntStream.range(0, columns.size()).forEach(i -> {
                val column = columns.get(i);
                val cell = row.createCell(i);

                cell.setCellValue(column.getValues().get(time).doubleValue());
                cell.setCellStyle(column.isHighlighted()
                        ? cellStyles.getHighlightedCellStyle()
                        : cellStyles.getCommonCellStyle());
            });
        });
    }

    private void generateHeaders(XSSFSheet sheet, List<Column> columns, HeaderCellStyles cellStyles) {
        val headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40);

        for (int i = 0; i < columns.size(); i++) {
            val headerCell = headerRow.createCell(i);

            headerCell.setCellValue(columns.get(i).getHeader());
            headerCell.setCellStyle(i == columns.size() - 1
                    ? cellStyles.getLastCellStyle()
                    : cellStyles.getCommonCellStyle());
        }
    }
}
