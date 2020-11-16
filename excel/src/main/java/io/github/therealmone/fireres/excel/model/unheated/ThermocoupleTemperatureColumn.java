package io.github.therealmone.fireres.excel.model.unheated;

import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceThermocoupleTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class ThermocoupleTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "ТП";
    private static final String CHART_TITLE = "Термопара ";

    private final Integer index;

    public ThermocoupleTemperatureColumn(Integer index, UnheatedSurfaceThermocoupleTemperature thermocoupleTemperature) {
        super(HEADER + (index + 1), false, thermocoupleTemperature);
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
