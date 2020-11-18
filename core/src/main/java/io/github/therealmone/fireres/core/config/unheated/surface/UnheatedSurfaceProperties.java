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

    private UnheatedSurfaceGroupProperties firstGroup;
    private UnheatedSurfaceSecondaryGroupProperties secondGroup;
    private UnheatedSurfaceSecondaryGroupProperties thirdGroup;

}
