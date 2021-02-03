package io.github.therealmone.fireres.unheated.surface.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;

public class UnheatedSurfaceServiceImpl implements UnheatedSurfaceService {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportPipeline;

    @Override
    public UnheatedSurfaceReport createReport(Sample sample) {
        val report = new UnheatedSurfaceReport(sample);

        report.setFirstGroup(new UnheatedSurfaceGroup());
        report.setSecondGroup(new UnheatedSurfaceGroup());
        report.setThirdGroup(new UnheatedSurfaceGroup());

        sample.putReport(report);

        reportPipeline.accept(report);

        return report;
    }

}
