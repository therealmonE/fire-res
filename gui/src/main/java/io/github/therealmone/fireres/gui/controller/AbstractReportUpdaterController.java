package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;

import java.util.UUID;

public abstract class AbstractReportUpdaterController extends AbstractController {

    @Inject
    private ReportExecutorService reportExecutorService;

    protected void updateReport(Runnable action) {
        reportExecutorService.runTask(getReportId(), getChartContainer(), action);
    }

    protected abstract ChartContainer getChartContainer();

    protected abstract UUID getReportId();

}
