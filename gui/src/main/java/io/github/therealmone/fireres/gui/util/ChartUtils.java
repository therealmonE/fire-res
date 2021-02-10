package io.github.therealmone.fireres.gui.util;

import com.sun.javafx.charts.Legend;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.PointSequence;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass
public class ChartUtils {

    public static final String RANDOM_COLOR_TEMPLATE = "-fx-stroke: rgba(%d, %d, %d, %f);";

    public static void addPointsToSeries(XYChart.Series<Number, Number> series, PointSequence<? extends Point<? extends Number>> points) {
        points.getValue().forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));
    }

    public static void randomizeColor(Node node, Double alpha) {
        node.setStyle(String.format(RANDOM_COLOR_TEMPLATE,
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                alpha));
    }

    public static void addLegendSymbolId(Chart chart, String expectedText, String symbolId) {
        chart.getChildrenUnmodifiable().forEach(node -> {
            if (node instanceof Legend) {
                val legend = (Legend) node;
                legend.getItems().forEach(item -> {
                    if (item.getText().equals(expectedText)) {
                        item.getSymbol().setId(symbolId);
                    }
                });
            }
        });
    }

}
