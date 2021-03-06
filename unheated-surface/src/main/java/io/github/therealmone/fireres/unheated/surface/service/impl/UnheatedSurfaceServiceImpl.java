package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.model.Group;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;

import java.util.UUID;

public class UnheatedSurfaceServiceImpl implements UnheatedSurfaceService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Override
    public UnheatedSurfaceReport createReport(UUID reportId, Sample sample) {
        val report = new UnheatedSurfaceReport(reportId, sample);

        report.setFirstGroup(new Group());
        report.setSecondGroup(new Group());
        report.setThirdGroup(new Group());

        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

}
