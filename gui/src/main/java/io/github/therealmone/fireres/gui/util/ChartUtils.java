package io.github.therealmone.fireres.gui.util;

import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.PointSequence;
import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChartUtils {

    public static void addPointsToSeries(XYChart.Series<Number, Number> series, PointSequence<? extends Point<? extends Number>> points) {
        points.getValue().forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));
    }

}
