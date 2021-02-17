package io.github.therealmone.fireres.unheated.surface.config;

import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceProperties implements ReportProperties {

    @Builder.Default
    private UnheatedSurfaceGroupProperties firstGroup = new UnheatedSurfaceGroupProperties();

    @Builder.Default
    private UnheatedSurfaceSecondaryGroupProperties secondGroup = new UnheatedSurfaceSecondaryGroupProperties();

    @Builder.Default
    private UnheatedSurfaceSecondaryGroupProperties thirdGroup = new UnheatedSurfaceSecondaryGroupProperties();

}
