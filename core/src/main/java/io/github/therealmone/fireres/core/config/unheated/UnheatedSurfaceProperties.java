package io.github.therealmone.fireres.core.config.unheated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceProperties {

    private UnheatedSurfaceGroup firstGroup;
    private UnheatedSurfaceGroup secondGroup;
    private UnheatedSurfaceThirdGroup thirdGroup;

}
