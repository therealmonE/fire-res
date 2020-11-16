package io.github.therealmone.fireres.excel.model.unheated;

import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceMeanTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class MeanTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Ср. ар.";
    private static final String CHART_TITLE = "Среднее значение температуры";

    public MeanTemperatureColumn(UnheatedSurfaceMeanTemperature meanTemperature) {
        super(HEADER, false, meanTemperature);
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
