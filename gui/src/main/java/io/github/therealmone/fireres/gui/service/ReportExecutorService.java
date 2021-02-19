package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.ChartContainer;

import java.util.UUID;

public interface ReportExecutorService {

    void runTask(UUID reportId, ChartContainer chartContainer, Runnable task);

}
