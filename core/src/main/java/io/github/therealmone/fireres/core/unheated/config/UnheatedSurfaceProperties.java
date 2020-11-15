package io.github.therealmone.fireres.core.unheated.config;

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
    private UnheatedSurfaceGroupProperties secondGroup;
    private UnheatedSurfaceThirdGroupProperties thirdGroup;

}
