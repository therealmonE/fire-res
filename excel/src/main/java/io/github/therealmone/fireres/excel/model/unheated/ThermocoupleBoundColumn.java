package io.github.therealmone.fireres.excel.model.unheated;

import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceThermocoupleBound;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public class ThermocoupleBoundColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "ТП max";
    private static final String CHART_TITLE = "Предельное значение температуры термопары";

    public ThermocoupleBoundColumn(UnheatedSurfaceThermocoupleBound thermocoupleBound) {
        super(HEADER, false, thermocoupleBound);
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
