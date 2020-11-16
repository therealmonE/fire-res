package io.github.therealmone.fireres.excel.model.unheated;

import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceMeanBound;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MeanBoundColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Ср. max";
    private static final String CHART_TITLE = "Предельное значение средней температуры";

    public MeanBoundColumn(UnheatedSurfaceMeanBound meanBound) {
        super(HEADER, false, meanBound);
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
