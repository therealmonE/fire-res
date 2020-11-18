package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.excess.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MaxAllowedPressureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Pмакс";
    private static final String CHART_TITLE = "Максимальный допуск избыточного давления";

    public MaxAllowedPressureColumn(MaxAllowedPressure maxAllowedPressure) {
        super(HEADER, false, maxAllowedPressure);
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
