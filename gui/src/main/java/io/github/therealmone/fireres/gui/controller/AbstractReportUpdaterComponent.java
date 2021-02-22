package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;

public abstract class AbstractReportUpdaterComponent<N> extends AbstractComponent<N> {

    @Inject
    private ReportExecutorService reportExecutorService;

    protected void updateReport(Runnable action, Node... nodesToLock) {
        updateReport(action, Arrays.asList(nodesToLock));
    }

    protected void updateReport(Runnable action, List<Node> nodesToLock) {
        reportExecutorService.runTask(ReportTask.builder()
                .reportId(getReportId())
                .action(action)
                .chartContainers(singletonList(getChartContainer()))
                .nodesToLock(nodesToLock)
                .build());
    }

    protected abstract ChartContainer getChartContainer();

    protected abstract UUID getReportId();

}
