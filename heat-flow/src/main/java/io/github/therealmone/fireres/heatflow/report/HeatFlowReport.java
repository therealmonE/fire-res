package io.github.therealmone.fireres.heatflow.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowReport implements Report {

    private List<HeatFlowSample> samples;

}