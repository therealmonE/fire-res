package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.style.data.DataCellStyles;
import io.github.therealmone.fireres.excel.style.data.HeaderCellStyles;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class AbstractExcelSheet implements ExcelSheet {

    private static final Integer REPORT_INTERVAL = 3;

    @Inject
    @Time
    protected Integer time;

    protected final String sheetName;

    @Override
    public void create(XSSFWorkbook workbook) {
        val sheet = workbook.createSheet(sheetName);
        val excelReports = createExcelReports();

        for (int i = 0; i < excelReports.size(); i++) {
            val excelReport = excelReports.get(i);
            val columns = excelReport.getData();
            val position = i * (time + REPORT_INTERVAL + 1);

            generateHeaders(sheet, columns, new HeaderCellStyles(workbook), position);
            generateData(sheet, columns, new DataCellStyles(workbook), position);
            generateChart(sheet, excelReport, position);
        }
    }

    protected void generateHeaders(XSSFSheet sheet, List<Column> columns,
                                   HeaderCellStyles cellStyles, Integer position) {

        val headerRow = sheet.createRow(position);
        headerRow.setHeightInPoints(40);

        for (int i = 0; i < columns.size(); i++) {
            val headerCell = headerRow.createCell(i);

            headerCell.setCellValue(columns.get(i).getHeader());
            headerCell.setCellStyle(i == columns.size() - 1
                    ? cellStyles.getLastCellStyle()
                    : cellStyles.getCommonCellStyle());
        }
    }

    protected void generateData(XSSFSheet sheet, List<Column> columns,
                                DataCellStyles cellStyles, Integer position) {

        IntStream.range(0, time).forEach(t -> {
            val row = sheet.createRow(position + t + 1);

            IntStream.range(0, columns.size()).forEach(i -> {
                val column = columns.get(i);
                val cell = row.createCell(i);

                cell.setCellValue(column.getValues().get(t).doubleValue());
                cell.setCellStyle(column.isHighlighted()
                        ? cellStyles.getHighlightedCellStyle()
                        : cellStyles.getCommonCellStyle());
            });
        });
    }

    protected void generateChart(XSSFSheet sheet, ExcelReport report, Integer position) {
        val chart = report.getChart();
        chart.plot(sheet, position);
    }

    protected abstract List<ExcelReport> createExcelReports();

}
