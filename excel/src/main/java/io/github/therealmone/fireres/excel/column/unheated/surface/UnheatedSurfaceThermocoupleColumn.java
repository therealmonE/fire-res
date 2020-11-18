package io.github.therealmone.fireres.excel.column.unheated.surface;

import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleTemperature;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class UnheatedSurfaceThermocoupleColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "ТП";
    private static final String CHART_TITLE = "Термопара ";

    private final Integer index;

    public UnheatedSurfaceThermocoupleColumn(Integer index, UnheatedSurfaceThermocoupleTemperature thermocouple) {
        super(HEADER + (index + 1), false, thermocouple);
        this.index = index;
    }

    @Override
    public String getChartLegendTitle() {
        return CHART_TITLE + (index + 1);
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new DefaultDataLineProperties();
    }
}
