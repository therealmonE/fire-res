package io.github.therealmone.fireres.unheated.surface.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceReport implements Report {

    private List<UnheatedSurfaceSample> samples;

}
