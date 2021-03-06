package io.github.therealmone.fireres.excel.column.unheated.surface;

import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import io.github.therealmone.fireres.unheated.surface.model.MaxAllowedMeanTemperature;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class UnheatedSurfaceMeanBoundColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Ср. max";
    private static final String CHART_TITLE = "Предельное значение средней температуры";

    public UnheatedSurfaceMeanBoundColumn(MaxAllowedMeanTemperature thermocouple) {
        super(HEADER, false, thermocouple);
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
