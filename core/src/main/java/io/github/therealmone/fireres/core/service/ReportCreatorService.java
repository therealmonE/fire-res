package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;

public interface ReportCreatorService<R extends Report> {

    R createReport(Sample sample);

}
