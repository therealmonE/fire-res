package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public abstract class AbstractReportUpdaterComponent<N>
        extends AbstractComponent<N>
        implements ReportUpdater {

    @Inject
    private ReportExecutorService reportExecutorService;

    @Override
    public void updateReport(Runnable action, Node... nodesToLock) {
        updateReport(action, Arrays.asList(nodesToLock));
    }

    @Override
    public void updateReport(Runnable action, List<Node> nodesToLock) {
        reportExecutorService.runTask(ReportTask.builder()
                .updatingElementId(getUpdatingElementId())
                .action(action)
                .chartContainers(singletonList(getChartContainer()))
                .nodesToLock(nodesToLock)
                .build());
    }

}
