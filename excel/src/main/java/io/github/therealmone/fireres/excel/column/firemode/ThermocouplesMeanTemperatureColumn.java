package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class ThermocouplesMeanTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тср - %s";
    private static final String CHART_TITLE = "Среднее значение температуры в печи - %s";

    private final String sampleName;

    public ThermocouplesMeanTemperatureColumn(String sampleName, ThermocoupleMeanTemperature thermocoupleMeanTemperature) {
        super(String.format(HEADER, sampleName), true, thermocoupleMeanTemperature);
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
