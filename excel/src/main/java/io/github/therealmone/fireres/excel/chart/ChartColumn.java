package io.github.therealmone.fireres.excel.chart;

import org.apache.poi.xddf.usermodel.XDDFLineProperties;

public interface ChartColumn {

    String getChartLegendTitle();

    XDDFLineProperties getLineProperties();

}