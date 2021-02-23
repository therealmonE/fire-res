package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.StandardTemperatureLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class StandardTemperatureColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Тст.пож. - %s";
    private static final String CHART_TITLE = "Стандартный режим пожара - %s";

    private final String sampleName;

    public StandardTemperatureColumn(String sampleName, StandardTemperature standardTemperature) {
        super(String.format(HEADER, sampleName), true, standardTemperature);
        this.sampleName = sampleName;
    }

    @Override
    public String getChartLegendTitle() {
        return String.format(CHART_TITLE, sampleName);
    }

    @Override
    public XDDFLineProperties getLineProperties() {
        return new StandardTemperatureLineProperties();
    }
}
