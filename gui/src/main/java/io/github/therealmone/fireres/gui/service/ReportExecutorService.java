package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.model.ReportTask;

public interface ReportExecutorService {

    void runTask(ReportTask task);

    void addListener(ReportUpdateListener listener);

    void removeListener(ReportUpdateListener listener);

}
