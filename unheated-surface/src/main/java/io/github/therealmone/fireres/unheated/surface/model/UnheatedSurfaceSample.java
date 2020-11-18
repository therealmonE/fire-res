package io.github.therealmone.fireres.unheated.surface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceSample {

    private UnheatedSurfaceGroup firstGroup;
    private UnheatedSurfaceGroup secondGroup;
    private UnheatedSurfaceGroup thirdGroup;

}
