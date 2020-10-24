package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.StandardTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.chart.lines.StandardTemperatureLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class StandardTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тст.пож.";
    private static final String CHART_TITLE = "Стандартный режим пожара";

    public StandardTemperatureColumn(StandardTemperature standardTemperature) {
        super(HEADER, standardTemperature);
    }

    @Override
    public String getChartLegendTitle() {
        return CHART_TITLE;
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new StandardTemperatureLineProperties();
    }
}
