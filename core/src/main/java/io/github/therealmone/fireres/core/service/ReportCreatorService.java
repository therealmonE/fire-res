package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;

import java.util.UUID;

public interface ReportCreatorService<R extends Report> {

    default R createReport(Sample sample) {
        return createReport(UUID.randomUUID(), sample);
    }

    R createReport(UUID reportId, Sample sample);

}
