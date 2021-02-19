package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.GaussianBlur;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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

    @Override
    public void runTask(UUID reportId, ChartContainer chartContainer, Runnable task) {
        locks.putIfAbsent(reportId, new ReentrantLock(true));
        tasksRunning.putIfAbsent(reportId, new AtomicInteger(0));

        executorService.submit(() -> {
            val lock = locks.get(reportId);

            if (tasksRunning.get(reportId).get() == 0) {
                Platform.runLater(() -> showProgressIndicator(chartContainer));
            }

            try {
                if (lock.tryLock(15, TimeUnit.SECONDS)) {
                    tasksRunning.get(reportId).incrementAndGet();

                    task.run();
                    Thread.sleep(1000);
                    Platform.runLater(chartContainer::synchronizeChart);
                }
            } catch (Exception e) {
                log.error("Error while executing report {} task: ", reportId, e);
            } finally {
                lock.unlock();

                if (tasksRunning.get(reportId).decrementAndGet() == 0) {
                    Platform.runLater(() -> removeProgressIndicator(chartContainer));
                }
            }
        });
    }

    private void showProgressIndicator(ChartContainer chartContainer) {
        val indicator = new ProgressIndicator();

        chartContainer.getChart().setDisable(true);
        chartContainer.getChart().setEffect(new GaussianBlur());
        chartContainer.getStackPane().getChildren().add(indicator);

    }

    private void removeProgressIndicator(ChartContainer chartContainer) {
        chartContainer.getChart().setDisable(false);
        chartContainer.getChart().setEffect(null);
        chartContainer.getStackPane().getChildren().removeIf(child -> child instanceof ProgressIndicator);
    }
}
