package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.excel.chart.ExcelChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.report.ExcelReport;
import io.github.therealmone.fireres.excel.style.data.DataCellStyles;
import io.github.therealmone.fireres.excel.style.data.HeaderCellStyles;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractExcelSheet implements ExcelSheet {

    private static final Integer REPORT_INTERVAL = 3;

    @Override
    public void create(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet(getSheetName());

        for (int i = 0; i < getReports().size(); i++) {
            ExcelReport excelReport = getReports().get(i);
            Integer position = i * (getTime() + REPORT_INTERVAL + 1);

            List<Column> columns = excelReport.getData();

            generateHeaders(sheet, columns, new HeaderCellStyles(workbook), position);
            generateData(sheet, columns, new DataCellStyles(workbook), position);
            generateChart(sheet, excelReport, position);
        }
    }

    protected void generateHeaders(XSSFSheet sheet, List<Column> columns,
                                   HeaderCellStyles cellStyles, Integer position) {

        XSSFRow headerRow = sheet.createRow(position);
        headerRow.setHeightInPoints(40);

        for (int i = 0; i < columns.size(); i++) {
            XSSFCell headerCell = headerRow.createCell(i);

            headerCell.setCellValue(columns.get(i).getHeader());
            headerCell.setCellStyle(i == columns.size() - 1
                    ? cellStyles.getLastCellStyle()
                    : cellStyles.getCommonCellStyle());
        }
    }

    protected void generateData(XSSFSheet sheet, List<Column> columns,
                                DataCellStyles cellStyles, Integer position) {

        IntStream.range(0, getTime()).forEach(t -> {
            XSSFRow row = sheet.createRow(position + t + 1);

            IntStream.range(0, columns.size()).forEach(i -> {
                Column column = columns.get(i);
                XSSFCell cell = row.createCell(i);

                cell.setCellValue(column.getValues().get(t).doubleValue());
                cell.setCellStyle(column.isHighlighted()
                        ? cellStyles.getHighlightedCellStyle()
                        : cellStyles.getCommonCellStyle());
            });
        });
    }

    protected void generateChart(XSSFSheet sheet, ExcelReport report, Integer position) {
        ExcelChart chart = report.getChart();
        chart.plot(sheet, position);
    }

    protected abstract Integer getTime();

    protected abstract List<ExcelReport> getReports();

    protected abstract String getSheetName();

}
