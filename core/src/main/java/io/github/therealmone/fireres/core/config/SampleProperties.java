package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.core.config.heatflow.HeatFlowProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    @Optional
    private FireModeProperties fireMode;

    @Optional
    private UnheatedSurfaceProperties unheatedSurface;

    @Optional
    private HeatFlowProperties heatFlow;

}
