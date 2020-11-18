package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.StandardTemperatureLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class StandardTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тст.пож.";
    private static final String CHART_TITLE = "Стандартный режим пожара";

    public StandardTemperatureColumn(StandardTemperature standardTemperature) {
        super(HEADER, true, standardTemperature);
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
