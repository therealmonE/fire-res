package io.github.therealmone.fireres.excel.model.pressure;

import io.github.therealmone.fireres.core.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;


public class PminColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Pмин";
    private static final String CHART_TITLE = "Минимальный допуск избыточного давления";

    public PminColumn(MinAllowedPressure minAllowedPressure) {
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
