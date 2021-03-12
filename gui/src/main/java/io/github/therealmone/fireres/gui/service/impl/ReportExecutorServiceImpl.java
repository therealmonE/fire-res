package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import io.github.therealmone.fireres.gui.exception.NotNotifiableException;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.text.TextAlignment;
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
        val nodesToLock = task.getNodesToLock();

        locks.putIfAbsent(reportId, new ReentrantLock(true));
        tasksRunning.putIfAbsent(reportId, new AtomicInteger(0));

        if (tasksRunning.get(reportId).get() == 0) {
            showProgressIndicator(task.getChartContainers());
            lockElements(nodesToLock);
            lockGeneralParams();
        }

        tasksRunning.get(reportId).incrementAndGet();

        executorService.submit(() -> {
            val lock = locks.get(reportId);
            removeErrorNotification(task.getChartContainers());

            try {
                if (lock.tryLock(15, TimeUnit.SECONDS)) {
                    doTask(task);
                }
            } catch (NotNotifiableException e) {
                log.error("Error while executing report {} task: ", reportId, e);
            } catch (Exception e) {
                log.error("Error while executing report {} task: ", reportId, e);
                showErrorNotification(task.getChartContainers());
            } finally {
                if (tasksRunning.get(reportId).decrementAndGet() == 0) {
                    unlockElements(nodesToLock);
                    removeProgressIndicator(task.getChartContainers());
                }

                if (tasksRunning.values().stream().allMatch(count -> count.get() == 0)) {
                    unlockGeneralParams();
                }

                lock.unlock();
            }
        });
    }

    private void unlockElements(List<javafx.scene.Node> nodesToLock) {
        Platform.runLater(() -> nodesToLock.forEach(node -> node.setDisable(false)));
    }

    private void doTask(ReportTask task) {
        task.getAction().run();
        Platform.runLater(() -> task.getChartContainers().forEach(ChartContainer::synchronizeChart));
    }

    private void lockElements(List<javafx.scene.Node> nodesToLock) {
        nodesToLock.forEach(node -> node.setDisable(true));
    }

    private void lockGeneralParams() {
        generalParams.getComponent().setDisable(true);
    }

    private void unlockGeneralParams() {
        Platform.runLater(() -> generalParams.getComponent().setDisable(false));
    }

    private void showProgressIndicator(List<ChartContainer> chartContainers) {
        Platform.runLater(() -> chartContainers.forEach(chartContainer -> {
            chartContainer.getChart().setDisable(true);
            chartContainer.getChart().setEffect(new GaussianBlur());
            chartContainer.getStackPane().getChildren().add(new ProgressIndicator());
        }));
    }

    private void removeProgressIndicator(List<ChartContainer> chartContainers) {
        Platform.runLater(() -> chartContainers.forEach(chartContainer -> {
            chartContainer.getStackPane().getChildren().removeIf(child -> child instanceof ProgressIndicator);

            if (chartContainer.getStackPane().getChildren().size() == 1) {
                chartContainer.getChart().setDisable(false);
                chartContainer.getChart().setEffect(null);
            }
        }));
    }

    private void showErrorNotification(List<ChartContainer> chartContainers) {
        val error = new Label("Невозможно сгенерировать отчет с данными параметрами.\n"
                + "Измените параметры или обновите график.");

        error.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
        error.setTextAlignment(TextAlignment.CENTER);

        Platform.runLater(() -> chartContainers.forEach(chartContainer -> {
            chartContainer.getChart().setDisable(true);
            chartContainer.getChart().setEffect(new GaussianBlur());
            chartContainer.getStackPane().getChildren().add(error);
        }));
    }

    private void removeErrorNotification(List<ChartContainer> chartContainers) {
        Platform.runLater(() -> chartContainers.forEach(chartContainer -> {
            chartContainer.getStackPane().getChildren().removeIf(child -> child instanceof Label);

            if (chartContainer.getStackPane().getChildren().size() == 1) {
                chartContainer.getChart().setDisable(false);
                chartContainer.getChart().setEffect(null);
            }
        }));
    }
}
