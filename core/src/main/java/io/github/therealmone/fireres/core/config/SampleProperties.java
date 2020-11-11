package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.config.heatflow.HeatFlowProperties;
import io.github.therealmone.fireres.core.config.unheated.UnheatedSurfaceProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private FireModeProperties fireMode;
    private UnheatedSurfaceProperties unheatedSurface;
    private HeatFlowProperties heatFlow;

}
