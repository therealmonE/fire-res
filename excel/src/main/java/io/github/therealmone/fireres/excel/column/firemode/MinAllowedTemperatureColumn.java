package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MinAllowedTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тмин - %s";
    private static final String CHART_TITLE = "Минимальный допуск температуры - %s";

    private final String sampleName;

    public MinAllowedTemperatureColumn(String sampleName, MinAllowedTemperature minAllowedTemperature) {
        super(String.format(HEADER, sampleName), false, minAllowedTemperature);
        this.sampleName = sampleName;
    }

    @Override
    public String getChartLegendTitle() {
        return String.format(CHART_TITLE, sampleName);
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new DefaultDataLineProperties();
    }
}
