package io.github.therealmone.fireres.excel.model.pressure;

import io.github.therealmone.fireres.core.pressure.model.SamplePressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class DeltaColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Δ";
    private static final String CHART_TITLE = "Избыточное давление - Образец № ";

    private final Integer index;

    public DeltaColumn(Integer index, SamplePressure samplePressure) {
        super(HEADER + index, false, samplePressure);
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
