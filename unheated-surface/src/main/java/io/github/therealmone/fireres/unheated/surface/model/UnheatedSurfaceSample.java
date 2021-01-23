package io.github.therealmone.fireres.unheated.surface.model;

import io.github.therealmone.fireres.core.model.ReportSample;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UnheatedSurfaceSample implements ReportSample {

    private final UUID id;

    private UnheatedSurfaceGroup firstGroup;
    private UnheatedSurfaceGroup secondGroup;
    private UnheatedSurfaceGroup thirdGroup;

}
