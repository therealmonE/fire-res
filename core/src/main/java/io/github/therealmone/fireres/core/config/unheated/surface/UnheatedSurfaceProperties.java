package io.github.therealmone.fireres.core.config.unheated.surface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceProperties {

    @Builder.Default
    private UnheatedSurfaceGroupProperties firstGroup = new UnheatedSurfaceGroupProperties();

    @Builder.Default
    private UnheatedSurfaceSecondaryGroupProperties secondGroup = new UnheatedSurfaceSecondaryGroupProperties();

    @Builder.Default
    private UnheatedSurfaceSecondaryGroupProperties thirdGroup = new UnheatedSurfaceSecondaryGroupProperties();

}
