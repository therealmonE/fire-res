package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class ThermocouplesMeanTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тср";
    private static final String CHART_TITLE = "Среднее значение температуры в печи - Образец № ";

    private final Integer index;

    public ThermocouplesMeanTemperatureColumn(Integer index, ThermocoupleMeanTemperature thermocoupleMeanTemperature) {
        super(HEADER + index, true, thermocoupleMeanTemperature);
        this.index = index;
    }

    @Override
    public String getChartLegendTitle() {
        return CHART_TITLE + index;
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new DefaultDataLineProperties();
    }
}
