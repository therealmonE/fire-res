package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.DoublePointSequence;
import io.github.therealmone.fireres.excess.pressure.model.Pressure;
import io.github.therealmone.fireres.excel.chart.ChartColumn;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import io.github.therealmone.fireres.excel.style.chart.DefaultDataLineProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;

import java.util.stream.Collectors;

public class DeltaColumn extends PointSequenceColumn implements ChartColumn {

    private static final String HEADER = "Δ - %s";
    private static final String CHART_TITLE = "Избыточное давление - %s";

    private final Object sampleName;

    public DeltaColumn(String sampleName, Pressure pressure) {
        super(String.format(HEADER, sampleName), true,
                new DoublePointSequence(pressure.getValue().stream()
                        .map(p -> new DoublePoint(p.getTime(), p.getNormalizedValue()))
                        .collect(Collectors.toList())));

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
