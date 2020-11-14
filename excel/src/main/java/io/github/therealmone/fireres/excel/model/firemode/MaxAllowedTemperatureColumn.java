package io.github.therealmone.fireres.excel.model.firemode;

import io.github.therealmone.fireres.core.firemode.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MaxAllowedTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тмакс";
    private static final String CHART_TITLE = "Максимальный допуск температуры";

    public MaxAllowedTemperatureColumn(MaxAllowedTemperature maxAllowedTemperature) {
        super(HEADER, false, maxAllowedTemperature);
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
