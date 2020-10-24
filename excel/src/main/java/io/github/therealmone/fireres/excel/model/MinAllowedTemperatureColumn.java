package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.chart.lines.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MinAllowedTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тмин";
    private static final String CHART_TITLE = "Минимальный допуск температуры";

    public MinAllowedTemperatureColumn(MinAllowedTemperature minAllowedTemperature) {
        super(HEADER, minAllowedTemperature);
    }

    @Override
    public String getChartLegendTitle() {
        return CHART_TITLE;
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new DefaultDataLineProperties();
    }
}
