package io.github.therealmone.fireres.gui.synchronizer;

import io.github.therealmone.fireres.core.model.Report;
import javafx.scene.chart.LineChart;

public interface ChartSynchronizer<R extends Report> {

    void synchronize(LineChart<Number, Number> chart, R report);

}
