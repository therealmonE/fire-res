package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MinAllowedPressureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Δмин";
    private static final String CHART_TITLE = "Минимальный допуск избыточного давления";

    public MinAllowedPressureColumn(MinAllowedPressure minAllowedPressure) {
        super(HEADER, false, minAllowedPressure);
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
