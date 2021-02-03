package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import io.github.therealmone.fireres.core.config.excess.pressure.ExcessPressureProperties;
import io.github.therealmone.fireres.core.config.firemode.FireModeProperties;
import io.github.therealmone.fireres.core.config.unheated.surface.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.core.config.heatflow.HeatFlowProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleProperties {

    private final UUID id = UUID.randomUUID();

    private String name;

    @Optional
    @Builder.Default
    private FireModeProperties fireMode = new FireModeProperties();

    @Optional
    @Builder.Default
    private ExcessPressureProperties excessPressure = new ExcessPressureProperties();

    @Optional
    @Builder.Default
    private UnheatedSurfaceProperties unheatedSurface = new UnheatedSurfaceProperties();

    @Optional
    @Builder.Default
    private HeatFlowProperties heatFlow = new HeatFlowProperties();

}
