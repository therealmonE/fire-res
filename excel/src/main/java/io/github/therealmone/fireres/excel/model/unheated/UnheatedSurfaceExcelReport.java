package io.github.therealmone.fireres.excel.model.unheated;

import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.excel.chart.ExcelChart;
import io.github.therealmone.fireres.excel.chart.UnheatedSurfaceChart;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.TimeColumn;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class UnheatedSurfaceExcelReport implements ExcelReport {

    private final Integer time;
    private final List<Column> data;

    public UnheatedSurfaceExcelReport(Integer time, UnheatedSurfaceGroup group, Integer thermocoupleIndexShift) {
        this.data = createData(time, group, thermocoupleIndexShift);
        this.time = time;
    }

    private List<Column> createData(Integer time, UnheatedSurfaceGroup group, Integer thermocoupleIndexShift) {
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));

        for (int i = 0; i < group.getThermocoupleTemperatures().size(); i++) {
            val thermocouple = group.getThermocoupleTemperatures().get(i);
            columns.add(new ThermocoupleTemperatureColumn(thermocoupleIndexShift + i, thermocouple));
        }

        columns.add(new MeanTemperatureColumn(group.getMeanTemperature()));

        if (group.getMeanBound() != null) {
            columns.add(new MeanBoundColumn(group.getMeanBound()));
        }

        columns.add(new ThermocoupleBoundColumn(group.getThermocoupleBound()));

        return columns;
    }

    @Override
    public List<Column> getData() {
        return this.data;
    }

    @Override
    public ExcelChart getChart() {
        return new UnheatedSurfaceChart(time, getData());
    }

}
