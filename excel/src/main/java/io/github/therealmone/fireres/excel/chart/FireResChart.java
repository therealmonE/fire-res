package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.chart.lines.AxisGridLineProperties;
import io.github.therealmone.fireres.excel.chart.lines.AxisLineProperties;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.TimeColumn;
import lombok.val;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange;

public class FireResChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    public static void generateChart(XSSFSheet sheet, Integer time, List<Column> columns) {
        val drawing = sheet.createDrawingPatriarch();
        val anchor = createAnchor(drawing, columns.size());

        setBackground(drawing, anchor);

        val chart = drawing.createChart(anchor);
        createLegend(chart);

        val data = createData(chart);
        addDataSeries(sheet, time, columns, data);

        chart.plot(data);
    }

    private static void setBackground(XSSFDrawing drawing, XSSFClientAnchor anchor) {
        val shape = drawing.createSimpleShape(anchor);
        shape.setFillColor(255, 255, 255);
    }

    private static XDDFChartData createData(XSSFChart chart) {
        return chart.createData(ChartTypes.LINE, createCategoryAxis(chart), createValueAxis(chart));
    }

    private static XDDFValueAxis createValueAxis(XSSFChart chart) {
        val valueAxis = chart.createValueAxis(AxisPosition.LEFT);
        valueAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        valueAxis.setTitle("Температура, оС");
        valueAxis.setVisible(true);

        val shape = valueAxis.getOrAddShapeProperties();
        shape.setLineProperties(new AxisLineProperties());

        val grid = valueAxis.getOrAddMajorGridProperties();
        grid.setLineProperties(new AxisGridLineProperties());

        return valueAxis;
    }

    private static XDDFCategoryAxis createCategoryAxis(XSSFChart chart) {
        val categoryAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        categoryAxis.setTitle("Время, мин.");
        categoryAxis.setVisible(true);

        val shape = categoryAxis.getOrAddShapeProperties();
        shape.setLineProperties(new AxisLineProperties());

        val grid = categoryAxis.getOrAddMajorGridProperties();
        grid.setLineProperties(new AxisGridLineProperties());

        return categoryAxis;
    }

    private static void createLegend(XSSFChart chart) {
        val legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);
    }

    private static XSSFClientAnchor createAnchor(XSSFDrawing drawing, Integer columnCount) {
        return drawing.createAnchor(
                0, 0, 0, 0,
                columnCount + 1, 1, columnCount + WIDTH, HEIGHT);
    }

    private static void addDataSeries(XSSFSheet sheet, Integer time, List<Column> columns, XDDFChartData data) {
        val timeSource = dataSource(sheet, time, findColumnIndex(columns, TimeColumn.class));

        for (val column : collectChartColumns(columns)) {
            val dataSource = dataSource(sheet, time, column.getFirst());
            val series = (XDDFLineChartData.Series) data.addSeries(timeSource, dataSource);
            series.setMarkerStyle(MarkerStyle.NONE);
            series.setSmooth(true);
            series.setLineProperties(column.getSecond().getLineProperties());
            series.setTitle(column.getSecond().getChartLegendTitle(), null);
        }
    }

    private static List<Pair<Integer, ChartColumn>> collectChartColumns(List<Column> columns) {
        return IntStream.range(0, columns.size())
                .filter(i -> columns.get(i) instanceof ChartColumn)
                .mapToObj(i -> new Pair<>(i, (ChartColumn) columns.get(i)))
                .collect(Collectors.toList());
    }

    private static Integer findColumnIndex(List<Column> columns, Class<? extends Column> clazz) {
        return IntStream.range(0, columns.size())
                .filter(i -> columns.get(i).getClass() == clazz)
                .findFirst()
                .orElseThrow();
    }

    private static XDDFNumericalDataSource<Double> dataSource(XSSFSheet sheet, Integer time, Integer column) {
        return fromNumericCellRange(sheet,
                new CellRangeAddress(1, time, column, column));
    }

}
