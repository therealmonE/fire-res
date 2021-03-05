package io.github.therealmone.fireres.heatflow.config;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatFlowProperties implements ReportProperties {

    private Integer sensorCount;
    private Double bound;

    @Builder.Default
    private FunctionForm<Double> functionForm = new FunctionForm<>();

}
