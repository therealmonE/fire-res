package io.github.therealmone.fireres.excel.column.heat.flow;

import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowBound;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class HeatFlowBoundColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "ТП max";
    private static final String CHART_TITLE = "Предельное значение теплового потока";

    public HeatFlowBoundColumn(HeatFlowBound thermocouple) {
        super(HEADER, false, thermocouple);
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