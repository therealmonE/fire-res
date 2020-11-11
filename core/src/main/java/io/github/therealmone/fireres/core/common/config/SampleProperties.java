package io.github.therealmone.fireres.core.common.config;

import io.github.therealmone.fireres.core.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.core.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceProperties;
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
