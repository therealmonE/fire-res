package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.GaussianBlur;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReportExecutorServiceImpl implements ReportExecutorService {

    @Inject
    private ExecutorService executorService;

    private final Map<UUID, Lock> locks = new ConcurrentHashMap<>();
    private final Map<UUID, AtomicInteger> tasksRunning = new ConcurrentHashMap<>();

    @Inject
    private GeneralParams generalParams;

    @Override
    public void runTask(ReportTask task) {
        val reportId = task.getReportId();
        val chartContainers = task.getChartContainers();
        val nodesToLock = task.getNodesToLock();

        locks.putIfAbsent(reportId, new ReentrantLock(true));
        tasksRunning.putIfAbsent(reportId, new AtomicInteger(0));

        if (tasksRunning.get(reportId).get() == 0) {
            lockElements(chartContainers, nodesToLock);
            lockGeneralParams();
        }

        tasksRunning.get(reportId).incrementAndGet();

        executorService.submit(() -> {
            val lock = locks.get(reportId);

            try {
                if (lock.tryLock(15, TimeUnit.SECONDS)) {
                    doTask(task, chartContainers);
                }
            } catch (Exception e) {
                log.error("Error while executing report {} task: ", reportId, e);
            } finally {
                if (tasksRunning.get(reportId).decrementAndGet() == 0) {
                    unlockElements(chartContainers, nodesToLock);
                }

                if (tasksRunning.values().stream().allMatch(count -> count.get() == 0)) {
                    unlockGeneralParams();
                }

                lock.unlock();
            }
        });
    }

    private void unlockElements(List<ChartContainer> chartContainers, List<javafx.scene.Node> nodesToLock) {
        Platform.runLater(() -> {
            removeProgressIndicator(chartContainers);
            nodesToLock.forEach(node -> node.setDisable(false));
        });
    }

    private void doTask(ReportTask task, List<ChartContainer> chartContainers) throws InterruptedException {
        task.getAction().run();
        Platform.runLater(() -> chartContainers.forEach(ChartContainer::synchronizeChart));
    }

    private void lockElements(List<ChartContainer> chartContainers, List<javafx.scene.Node> nodesToLock) {
        showProgressIndicator(chartContainers);
        nodesToLock.forEach(node -> node.setDisable(true));
    }

    private void lockGeneralParams() {
        generalParams.getComponent().setDisable(true);
    }

    private void unlockGeneralParams() {
        Platform.runLater(() -> generalParams.getComponent().setDisable(false));
    }

    private void showProgressIndicator(List<ChartContainer> chartContainers) {
        chartContainers.forEach(chartContainer -> {
            chartContainer.getChart().setDisable(true);
            chartContainer.getChart().setEffect(new GaussianBlur());
            chartContainer.getStackPane().getChildren().add(new ProgressIndicator());
        });
    }

    private void removeProgressIndicator(List<ChartContainer> chartContainers) {
        chartContainers.forEach(chartContainer -> {
            chartContainer.getChart().setDisable(false);
            chartContainer.getChart().setEffect(null);
            chartContainer.getStackPane().getChildren().removeIf(child -> child instanceof ProgressIndicator);
        });
    }
}
