package io.github.therealmone.fireres.core.unheated.report;

import io.github.therealmone.fireres.core.common.report.Report;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceSample;
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
