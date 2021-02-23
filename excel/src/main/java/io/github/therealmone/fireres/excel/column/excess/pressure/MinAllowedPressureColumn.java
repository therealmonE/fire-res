package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.excess.pressure.model.MinAllowedPressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MinAllowedPressureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Δмин - %s";
    private static final String CHART_TITLE = "Минимальный допуск избыточного давления - %s";

    private final String sampleName;

    public MinAllowedPressureColumn(String sampleName, MinAllowedPressure minAllowedPressure) {
        super(String.format(HEADER, sampleName), false, minAllowedPressure);
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
