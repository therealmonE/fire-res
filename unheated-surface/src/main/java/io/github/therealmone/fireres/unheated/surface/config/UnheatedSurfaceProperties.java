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
    private PrimaryGroupProperties firstGroup = new PrimaryGroupProperties();

    @Builder.Default
    private SecondaryGroupProperties secondGroup = new SecondaryGroupProperties();

    @Builder.Default
    private SecondaryGroupProperties thirdGroup = new SecondaryGroupProperties();

}
