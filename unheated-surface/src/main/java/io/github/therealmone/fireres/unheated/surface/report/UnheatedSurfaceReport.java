package io.github.therealmone.fireres.unheated.surface.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UnheatedSurfaceReport implements Report<UnheatedSurfaceProperties> {

    private final Sample sample;
    private final UUID id = UUID.randomUUID();

    private UnheatedSurfaceGroup firstGroup;
    private UnheatedSurfaceGroup secondGroup;
    private UnheatedSurfaceGroup thirdGroup;

    @Override
    public UnheatedSurfaceProperties getProperties() {
        return sample.getSampleProperties()
                .getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow();
    }
}
