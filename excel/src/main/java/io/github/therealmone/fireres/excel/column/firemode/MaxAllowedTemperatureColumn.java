package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MaxAllowedTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тмакс - %s";
    private static final String CHART_TITLE = "Максимальный допуск температуры - %s";

    private final String sampleName;

    public MaxAllowedTemperatureColumn(String sampleName, MaxAllowedTemperature maxAllowedTemperature) {
        super(String.format(HEADER, sampleName), false, maxAllowedTemperature);
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
