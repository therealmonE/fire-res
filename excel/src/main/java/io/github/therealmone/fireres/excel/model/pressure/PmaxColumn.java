package io.github.therealmone.fireres.excel.model.pressure;

import io.github.therealmone.fireres.core.pressure.model.MaxAllowedPressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;


public class PmaxColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Pмакс";
    private static final String CHART_TITLE = "Максимальный допуск избыточного давления";

    public PmaxColumn(MaxAllowedPressure maxAllowedPressure) {
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
